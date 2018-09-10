package edu.purdue.bwilary.todolist;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * This Class is the data model of a todoitem (An entry of the todolist).
 * You need to implement 4 methods to return different Comparators.
 * A Comparator defines how two objects are compared. Comparators can be used for sorting a list.
 *
 * Search online to find (1) How to compare two date Strings in Java (HINT: Use SimpleDateFormat)
 *                           Note: The date String is in the format of "MM/dd/yy"
 *                       (2) How to compare two Strings alphabetically (String has a built-in method to do it)
 */

public class TodoItem implements Serializable{


    private String category;
    private String note;
    private String date;

    public TodoItem(String category, String note, String date) {
        this.category = category;
        this.note = note;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    static Comparator<TodoItem> getClosestDueComparator() {
        return new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem item1, TodoItem item2) {
                //         Returns -1 if item1's due date is closer than item2's
                //         Returns 0 if they have the same due date
                SimpleDateFormat f = new SimpleDateFormat("MM/dd/yy");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = f.parse(item1.getDate());
                    date2 = f.parse(item2.getDate());
                } catch (ParseException e) {}
                if (date1.before(date2)) {
                    return -1;
                } else if (date1.after(date2)) {
                    return 1;
                }
                return 0;
            }
        };
    }

    static Comparator<TodoItem> getFarthestDueComparator() {
        return new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem item1, TodoItem item2) {
                //         Returns -1 if item2's due date is closer than item1's
                //         Returns 0 if they have the same due date
                SimpleDateFormat f = new SimpleDateFormat("MM/dd/yy");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = f.parse(item1.getDate());
                    date2 = f.parse(item2.getDate());
                } catch (ParseException e) {}
                if (date1.before(date2)) {
                    return 1;
                } else if (date1.after(date2)) {
                    return -1;
                }
                return 0;
            }
        };
    }

    static Comparator<TodoItem> getAtoZComparator() {
        return new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem item1, TodoItem item2) {
                //         Returns -1 if item1's note is alphabetically smaller than item2's
                //         Returns 0 if they are equal
                if (item1.getNote().compareToIgnoreCase(item2.getNote()) < 0) {
                    return  -1;
                } else if (item1.getNote().compareToIgnoreCase(item2.getNote()) > 0) {
                    return 1;
                }
                return 0;
            }
        };
    }

    static Comparator<TodoItem> getZtoAComparator() {
        return new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem item1, TodoItem item2) {
                //         Returns -1 if item1's note is alphabetically larger than item2's
                //         Returns 0 if they are equal
                if (item1.getNote().compareToIgnoreCase(item2.getNote()) < 0) {
                    return  1;
                } else if (item1.getNote().compareToIgnoreCase(item2.getNote()) > 0) {
                    return -1;
                }
                return 0;
            }
        };
    }

}
