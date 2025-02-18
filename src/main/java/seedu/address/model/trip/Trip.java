package seedu.address.model.trip;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.diary.Diary;
import seedu.address.model.expenditure.ExpenditureList;
import seedu.address.model.inventory.InventoryList;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.itinerary.event.EventList;

/**
 * Represents a Trip in TravelPal.
 * Compulsory fields: name, startDate, endDate, destination, dayList, totalBudget
 */
public class Trip {
    public static final String MESSAGE_INVALID_DATETIME = "Start date should be before end date";

    // Compulsory Fields
    private final Name name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final TripId tripId;
    private final Location destination;
    private final DayList dayList;
    private final ExpenditureList expenditureList;
    private final Budget totalBudget;
    private final Diary diary;
    private final Photo photo;

    private final InventoryList inventoryList;

    /**
     * Constructs a trip.
     */
    public Trip(Name name, LocalDateTime startDate, LocalDateTime endDate, Location destination,
                Budget totalBudget, DayList dayList, ExpenditureList expenditureList,
                Diary diary, InventoryList inventoryList, Photo photo) {
        checkArgument(isValidDuration(startDate, endDate), MESSAGE_INVALID_DATETIME);
        this.name = name;
        this.startDate = startDate.toLocalDate().atStartOfDay();
        this.endDate = endDate.toLocalDate().atTime(23, 59);
        this.destination = destination;
        this.totalBudget = totalBudget;
        this.dayList = dayList;
        this.expenditureList = expenditureList;
        this.tripId = new TripId();
        this.diary = diary;
        this.photo = photo;
        this.inventoryList = inventoryList;
    }

    /**
     * Constructs a trip with optional fields
     */
    public Trip(Name name, LocalDateTime startDate, LocalDateTime endDate, Location destination,
                Budget totalBudget, DayList dayList, ExpenditureList expenditureList,
                Diary diary, InventoryList inventoryList, Optional<Photo> photo) {
        checkArgument(isValidDuration(startDate, endDate), MESSAGE_INVALID_DATETIME);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.totalBudget = totalBudget;
        this.dayList = dayList;
        this.expenditureList = expenditureList;
        this.tripId = new TripId();
        this.diary = diary;
        this.photo = photo.orElse(null);
        this.inventoryList = inventoryList;
    }

    /**
     * Creates a list of days upon first initialization.
     */
    public void initializeDayList() {
        int totalDays = endDate.getDayOfMonth() - startDate.getDayOfMonth() + 1;
        assert(totalDays > 0);
        this.dayList.internalList.clear();
        for (int i = 0; i < totalDays; i++) {
            LocalDateTime currentDay = startDate.plusDays(i);
            this.dayList.add(new Day(currentDay.withHour(0).withMinute(0),
                    currentDay.withHour(23).withMinute(59),
                    Optional.empty(),
                    destination,
                    Optional.empty(),
                    new EventList(currentDay)));
        }
    }

    public boolean isValidDuration(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.isBefore(endDate);
    }

    //Compulsory field getters
    public Name getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public TripId getTripId() {
        return tripId;
    }

    public Location getDestination() {
        return destination;
    }

    public DayList getDayList() {
        return dayList;
    }

    public ExpenditureList getExpenditureList() {
        return expenditureList;
    }

    public Budget getBudget() {
        return totalBudget;
    }

    public InventoryList getInventoryList() {
        return inventoryList;
    }

    public Diary getDiary() {
        return diary;
    }

    // Optional fields
    public Optional<Photo> getPhoto() {
        return Optional.ofNullable(photo);
    }

    /**
     * Returns true if both {@link Trip} contain the same name, location, and starting, ending dates.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameTrip(Trip otherTrip) {
        if (otherTrip == this) {
            return true;
        } else {
            return otherTrip != null
                    && otherTrip.getName().equals(getName())
                    && otherTrip.getStartDate().equals(getStartDate())
                    && otherTrip.getEndDate().equals(getEndDate())
                    && otherTrip.getDestination().equals(getDestination());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Trip)) {
            return false;
        }

        Trip otherTrip = (Trip) other;
        return otherTrip.getName().equals(getName())
                && otherTrip.getStartDate().equals(getStartDate())
                && otherTrip.getEndDate().equals(getEndDate())
                && otherTrip.getDestination().equals(getDestination())
                && otherTrip.getBudget().equals(getBudget())
                && otherTrip.getDayList().equals(getDayList())
                && otherTrip.getDiary().equals(getDiary())
                && otherTrip.getExpenditureList().equals(getExpenditureList());
    }

    /**
     * Checks whether this trip is clashing with {@code other}.
     *
     * @param other The other trip instance to check.
     * @return Boolean of whether the trip clashes.
     */
    public boolean isClashingWith(Trip other) {
        return (this.getStartDate().compareTo(other.getStartDate()) >= 0
                && this.getStartDate().compareTo(other.getEndDate()) <= 0)
                || (this.getEndDate().compareTo(other.getStartDate()) >= 0
                && this.getEndDate().compareTo(other.getEndDate()) <= 0);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(name.toString())
                .append(" From: ")
                .append(ParserDateUtil.getDisplayTime(startDate))
                .append(" To: ")
                .append(ParserDateUtil.getDisplayTime(endDate))
                .append(" Destination: ")
                .append(destination.toString())
                .append(" Total Budget: ")
                .append(totalBudget.toString())
                .append(" Image Path: ")
                .append(photo == null ? "default image" : photo.getImageFilePath());

        return builder.toString();
    }

}
