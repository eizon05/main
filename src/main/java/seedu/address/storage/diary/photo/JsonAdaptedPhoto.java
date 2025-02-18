package seedu.address.storage.diary.photo;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.photo.Photo;

/**
 * Jackson-friendly version of {@link Photo}.
 */
public class JsonAdaptedPhoto {
    private static final String MISSING_FIELD_MESSAGE_FORMAT = "One of the fields provided is invalid!\n"
            + "Cause: %1$s";

    private final String imagePath;
    private final String description;
    private final LocalDateTime dateTaken;

    /**
     * Constructs a {@code JsonAdaptedDiary} with the given diary details.
     */
    @JsonCreator
    public JsonAdaptedPhoto(
            @JsonProperty("imagePath") String imagePath,
            @JsonProperty("description") String description,
            @JsonProperty("dateTaken") LocalDateTime dateTaken) {
        requireAllNonNull(imagePath, description, dateTaken);
        this.imagePath = imagePath;
        this.description = description;
        this.dateTaken = dateTaken;
    }

    /**
     * Converts a given {@code Diary} into this class for Jackson use.
     */
    public JsonAdaptedPhoto(Photo source) {
        requireNonNull(source);
        this.imagePath = source.getImageFilePath();
        this.description = source.getDescription();
        this.dateTaken = source.getDateTaken();
    }

    /**
     * Converts this Jackson-friendly adapted diary object into the model's {@code Diary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted diary.
     */
    public Photo toModelType() throws IllegalValueException {
        Photo photo;
        try {
            photo = new Photo(imagePath, description, dateTaken);
        } catch (IllegalArgumentException ex) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ex.getMessage()));
        }

        return photo;
    }
}
