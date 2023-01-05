package com.example.unnatiandroidpractical;

import androidx.annotation.NonNull;

import com.cleverpush.service.CleverPushFcmListenerService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FirebaseMessagService extends FirebaseMessagingService {

    private final List<FirebaseMessagingService> messagingServices = new ArrayList<>(2);

    public FirebaseMessagService() {
        messagingServices.add(new CleverPushFcmListenerService());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        delegate(service -> {
            injectContext(service);
            service.onNewToken(token);
        });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        delegate(service -> {
            injectContext(service);
            service.onMessageReceived(remoteMessage);
        });
    }

    private void delegate(CPAction<FirebaseMessagingService> action) {
        for (FirebaseMessagingService service : messagingServices) {
            action.run(service);
        }
    }

    private void injectContext(FirebaseMessagingService service) {
        setField(service, "mBase", this);
    }

    private boolean setField(Object targetObject, String fieldName, Object fieldValue) {
        Field field;
        try {
            field = targetObject.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        Class superClass = targetObject.getClass().getSuperclass();
        while (field == null && superClass != null) {
            try {
                field = superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                superClass = superClass.getSuperclass();
            }
        }
        if (field == null) {
            return false;
        }
        field.setAccessible(true);
        try {
            field.set(targetObject, fieldValue);
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    interface CPAction<T> {
        void run(T t);
    }
}
