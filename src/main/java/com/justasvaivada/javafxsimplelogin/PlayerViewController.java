package com.justasvaivada.javafxsimplelogin;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerViewController implements Initializable {

    @FXML
    public Label displayUsernameLabel;
    @FXML
    public Label songNameLabel;
    @FXML
    private ToggleButton muteBtn;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Button playBtn;
    @FXML
    private Button pauseBtn;

    private Media media;
    private MediaPlayer mediaPlayer;
    private int songIndex;
    private File[] files;
    private boolean atEndOfMedia;
    private Duration duration;
    private boolean stopRequested;
    private String path;



    private final LoginController loginController = new LoginController();

    private void init() {
        displayUsernameLabel.setText(loginController.getUsername());
        displayUsernameLabel.setVisible(true);
        path = SceneController.pathVar;

        files = FileController.getMusicFiles(new File(path));
        assert files != null;
        File song = FileController.getSong(files, 0);
        songNameLabel.setText(FileController.getSongName(song));

        media = new Media(FileController.getSong(files, songIndex).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songNameLabel.setText(FileController.getSong(files, songIndex).getName());
        mediaPlayer.play();


        mediaPlayer.setOnReady(() -> {
            duration = mediaPlayer.getMedia().getDuration();
            updateValues();
        });

        mediaPlayer.setOnEndOfMedia(this::nextSong);

        volumeSlider.valueProperty().addListener(observable -> {
            if (volumeSlider.isValueChanging()){
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });

        playBtn.setOnAction(event -> {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                return;
            }
            if (status == MediaPlayer.Status.PAUSED
                    || status == MediaPlayer.Status.READY
                    || status == MediaPlayer.Status.STOPPED) {
                if (atEndOfMedia) {
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    atEndOfMedia = false;
                    mediaPlayer.currentTimeProperty().addListener(observable -> updateValues());
                }
                mediaPlayer.currentTimeProperty().addListener(observable -> updateValues());
                mediaPlayer.play();
            }
        });

        pauseBtn.setOnAction(event -> {
            mediaPlayer.pause();
            stopRequested = true;
            mediaPlayer.currentTimeProperty().addListener(observable -> updateValues());
        });
    }

    protected void updateValues() {
        if (volumeSlider != null){
            Platform.runLater(() -> {
                if (!volumeSlider.isValueChanging()) {
                    volumeSlider.setValue((int)Math.round(mediaPlayer.getVolume() * 100));
                }
            });
        }
    }

    public void muteSong () {
        mediaPlayer.setMute(muteBtn.isSelected());
    }

    public void nextSong () {
        if (songIndex < files.length - 1) {
            songIndex++;
        } else {
            songIndex = 0;
        }
        mediaPlayer.stop();
        media = new Media(FileController.getSong(files, songIndex).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songNameLabel.setText(FileController.getSong(files, songIndex).getName());
        mediaPlayer.play();
    }


    public void previousSong () {
        if (songIndex > 0) {
            songIndex--;
        } else {
            songIndex = files.length - 1;
        }
        mediaPlayer.stop();
        media = new Media(FileController.getSong(files, songIndex).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songNameLabel.setText(FileController.getSong(files, songIndex).getName());
        mediaPlayer.play();
    }

    public void setPath (String pathToMusic) {
        this.path = pathToMusic;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }
}
