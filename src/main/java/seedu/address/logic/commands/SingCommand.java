package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class SingCommand extends Command {

    public static final String COMMAND_WORD = "sing";

    private static class LyricLine {
        final double atSec;   // when to show (seconds from start)
        final String text;
        LyricLine(double atSec, String text) { this.atSec = atSec; this.text = text; }
    }

    private Timeline lyricsTimeline;
    private MediaPlayer player;

    /**
     * lyrics for the song that could possibly be played in future
     */
    private final java.util.List<LyricLine> LYRICS1 = java.util.List.of(
            new LyricLine(0.3,  "希望你以后不会后悔没选择我"),
            new LyricLine(6.4,  "也相信你有更好的生活"),
            new LyricLine(13.0,  "我会在心里"),
            new LyricLine(15.5,  "默默地为你而执着~")
    );

    @Override
    public CommandResult execute(Model model) throws CommandException {

        // setup media
        String path = getClass().getResource("/audio/song1.wav").toExternalForm();
        player = new MediaPlayer(new Media(path));
        player.setStopTime(Duration.seconds(20));

        // lyric timeline
        if (lyricsTimeline != null) lyricsTimeline.stop();
        lyricsTimeline = new Timeline();
        lyricsTimeline.setCycleCount(1);

        player.play();
        lyricsTimeline.play();

        // cleanup when media ends
        player.setOnEndOfMedia(() -> {
            if (lyricsTimeline != null) lyricsTimeline.stop();
        });

        return new CommandResult("Start the music!\n" +
                "希望你以后不会后悔没选择我\n" +
                "也相信你有更好的生活\n" +
                "我会在心里\n" +
                "默默地为你而执着~");
    }
}
