package tuespotsolutions.com.youwave.player.playqueue.events;

public class InitEvent implements PlayQueueEvent {
    @Override
    public PlayQueueEventType type() {
        return PlayQueueEventType.INIT;
    }
}
