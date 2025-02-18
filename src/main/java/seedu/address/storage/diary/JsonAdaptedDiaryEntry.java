package seedu.address.storage.diary;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.FXCollections;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.photo.Photo;
import seedu.address.model.diary.photo.PhotoList;
import seedu.address.storage.diary.photo.JsonAdaptedPhoto;

/**
 * Jackson-friendly version of {@link DiaryEntry}.
 */
class JsonAdaptedDiaryEntry {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diary entry's %s field is missing!";

    private final int dayIndex;
    private final String diaryText;
    private final List<JsonAdaptedPhoto> photos;

    /**
     * Constructs a {@code JsonAdaptedDiary} with the given diary details.
     */
    @JsonCreator
    public JsonAdaptedDiaryEntry(
            @JsonProperty("dayIndex") int dayOneBasedIndex,
            @JsonProperty("diaryText") String diaryText,
            @JsonProperty("photos") List<JsonAdaptedPhoto> photos) {
        requireAllNonNull(dayOneBasedIndex, diaryText);
        this.dayIndex = dayOneBasedIndex;
        this.diaryText = diaryText;
        this.photos = FXCollections.observableArrayList();
        if (photos != null) {
            this.photos.addAll(photos);
        }
    }

    /**
     * Converts a given {@code DiaryEntry} into this class for Jackson use.
     */
    public JsonAdaptedDiaryEntry(DiaryEntry source) {
        requireNonNull(source);
        this.dayIndex = source.getDayNumber();
        this.diaryText = source.getDiaryText();
        this.photos = FXCollections.observableArrayList();
        for (Photo photo : source.getPhotoList().getObservablePhotoList()) {
            this.photos.add(new JsonAdaptedPhoto(photo));
        }
    }

    /**
     * Converts this Jackson-friendly adapted diary entry object into the model's {@code DiaryEntry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted diary entry.
     */
    public DiaryEntry toModelType() throws IllegalValueException {
        List<Photo> photoList = new ArrayList<Photo>();
        for (JsonAdaptedPhoto photo : photos) {
            photoList.add(photo.toModelType());
        }
        PhotoList modelPhotoList = new PhotoList(photoList);

        return new DiaryEntry(Index.fromOneBased(dayIndex), modelPhotoList, diaryText);
    }
}
