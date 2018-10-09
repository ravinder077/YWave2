package tuespotsolutions.com.youwave.local.holder;

import android.view.ViewGroup;

import tuespotsolutions.com.youwave.database.LocalItem;
import tuespotsolutions.com.youwave.database.playlist.model.PlaylistRemoteEntity;
import tuespotsolutions.com.youwave.extractor.NewPipe;
import tuespotsolutions.com.youwave.local.LocalItemBuilder;
import tuespotsolutions.com.youwave.util.ImageDisplayConstants;
import tuespotsolutions.com.youwave.util.Localization;

import java.text.DateFormat;

public class RemotePlaylistItemHolder extends PlaylistItemHolder {
    public RemotePlaylistItemHolder(LocalItemBuilder infoItemBuilder, ViewGroup parent) {
        super(infoItemBuilder, parent);
    }

    @Override
    public void updateFromItem(final LocalItem localItem, final DateFormat dateFormat) {
        if (!(localItem instanceof PlaylistRemoteEntity)) return;
        final PlaylistRemoteEntity item = (PlaylistRemoteEntity) localItem;

        itemTitleView.setText(item.getName());
        itemStreamCountView.setText(String.valueOf(item.getStreamCount()));
        itemUploaderView.setText(Localization.concatenateStrings(item.getUploader(),
                NewPipe.getNameOfService(item.getServiceId())));

        itemBuilder.displayImage(item.getThumbnailUrl(), itemThumbnailView,
                ImageDisplayConstants.DISPLAY_PLAYLIST_OPTIONS);

        super.updateFromItem(localItem, dateFormat);
    }
}
