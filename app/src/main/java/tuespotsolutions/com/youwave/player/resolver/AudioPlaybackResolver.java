package tuespotsolutions.com.youwave.player.resolver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.source.MediaSource;

import tuespotsolutions.com.youwave.extractor.MediaFormat;
import tuespotsolutions.com.youwave.extractor.stream.AudioStream;
import tuespotsolutions.com.youwave.extractor.stream.StreamInfo;
import tuespotsolutions.com.youwave.player.helper.PlayerDataSource;
import tuespotsolutions.com.youwave.player.helper.PlayerHelper;
import tuespotsolutions.com.youwave.util.ListHelper;

public class AudioPlaybackResolver implements PlaybackResolver {

    @NonNull private final Context context;
    @NonNull private final PlayerDataSource dataSource;

    public AudioPlaybackResolver(@NonNull final Context context,
                                 @NonNull final PlayerDataSource dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }

    @Override
    @Nullable
    public MediaSource resolve(@NonNull StreamInfo info) {
        final MediaSource liveSource = maybeBuildLiveMediaSource(dataSource, info);
        if (liveSource != null) return liveSource;

        final int index = ListHelper.getDefaultAudioFormat(context, info.getAudioStreams());
        if (index < 0 || index >= info.getAudioStreams().size()) return null;

        final AudioStream audio = info.getAudioStreams().get(index);
        final MediaSourceTag tag = new MediaSourceTag(info);
        return buildMediaSource(dataSource, audio.getUrl(), PlayerHelper.cacheKeyOf(info, audio),
                MediaFormat.getSuffixById(audio.getFormatId()), tag);
    }
}
