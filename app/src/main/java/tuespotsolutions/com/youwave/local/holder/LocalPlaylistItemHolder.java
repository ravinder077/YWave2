package tuespotsolutions.com.youwave.local.holder;

import android.view.View;
import android.view.ViewGroup;

import tuespotsolutions.com.youwave.database.LocalItem;
import tuespotsolutions.com.youwave.database.playlist.PlaylistMetadataEntry;
import tuespotsolutions.com.youwave.local.LocalItemBuilder;
import tuespotsolutions.com.youwave.util.ImageDisplayConstants;

import java.text.DateFormat;

public class LocalPlaylistItemHolder extends PlaylistItemHolder {

    public LocalPlaylistItemHolder(LocalItemBuilder infoItemBuilder, ViewGroup parent) {
        super(infoItemBuilder, parent);
    }

    @Override
    public void updateFromItem(final LocalItem localItem, final DateFormat dateFormat) {
        if (!(localItem instanceof PlaylistMetadataEntry)) return;
        final PlaylistMetadataEntry item = (PlaylistMetadataEntry) localItem;

        itemTitleView.setText(item.name);
        itemStreamCountView.setText(String.valueOf(item.streamCount));
        itemUploaderView.setVisibility(View.INVISIBLE);

        itemBuilder.displayImage(item.thumbnailUrl, itemThumbnailView,
                ImageDisplayConstants.DISPLAY_PLAYLIST_OPTIONS);

        super.updateFromItem(localItem, dateFormat);
    }
}
