package ca.sfu.cmpt276.dungeon;

import ca.sfu.cmpt276.dungeon.gameobject.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.Objects;

public class DrawEntity<T extends GameObject> {

    private final Pane entityLayer;
    private final Map<T, ImageView> views = new HashMap<>();

    public DrawEntity(Pane entityLayer) {
        this.entityLayer = entityLayer;
    }

    public void draw(List<T> list, String imgLoc, int TileSize) {
        // monsters
        for (T t : list) {
            ImageView view = views.computeIfAbsent(t, entity -> createEntityView(imgLoc, TileSize));
            // Position in tile coords
            view.setLayoutX(t.getX() * TileSize);
            view.setLayoutY(t.getY() * TileSize);
        }

        remove(list);
    }

    private void remove(List<T> list) {
        views.keySet().removeIf(t -> {
            if (!list.contains(t)) {
                entityLayer.getChildren().remove(views.get(t));
                return true;
            }
            return false;
        });
    }

    public void draw(T ent, String imgLoc, int TileSize) {
        // monsters
        ImageView view = views.computeIfAbsent(ent, entity -> createEntityView(imgLoc, TileSize));
        // Position in tile coords
        view.setLayoutX(ent.getX() * TileSize);
        view.setLayoutY(ent.getY() * TileSize);

    }

    /**
     * Creates an ImageView for a entity and adds it to the entity layer.
     * The image is loaded from the given path to a png and resized to fit one tile.
     * This is used so entities can have a picture instead of a circle.
     *
     * @return an ImageView displaying the entity image
     */
    private ImageView createEntityView(String imageLocation, int TileSize) {

        Image gif = new Image(Objects.requireNonNull(
                getClass().getResource(imageLocation)
        ).toExternalForm());

        ImageView iv = new ImageView(gif);
        iv.setFitWidth(TileSize);
        iv.setFitHeight(TileSize);
        iv.setPreserveRatio(false);   // fill the tile exactly
        iv.setSmooth(false);          // pixel-art crisp
        entityLayer.getChildren().add(iv);
        return iv;
    }


}