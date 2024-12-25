package test;
import java.util.Date;

public class Message {
    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

    public Message(String s){
        this.data = s.getBytes();
        this.asText = s;
        this.date = new Date();
        if(isConvertableToDouble(s)){
            this.asDouble = Double.parseDouble(s);
        }
        else{
            this.asDouble = Double.NaN;
        }
    }

    public Message(double d){
        this(Double.toString(d));
    }

    public Message(byte[] b){
        this(new String(b));
    }

    private boolean isConvertableToDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;  // If no exception is thrown, it's a valid double
        } catch (NumberFormatException e) {
            return false; // If an exception is thrown, it's not a valid double
        }
    }
}
