package simpleservlet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Entity
public class Convert {
    @GeneratedValue
    @Id
    private long id;

    @Column
    @NotNull
    private int inputNumber;
    @Column
    private int convertedNumber;

    public Convert() {}

    public Convert(int inputNumber, int convertedNumber) {
      this.inputNumber = inputNumber;
      this.convertedNumber = convertedNumber;
    }

    public long getId() {
        return id;
    }

    public int getInput() {
        return inputNumber;
    }

    public void setInput(int inputNumber) {
        this.inputNumber = inputNumber;
    }

    public int getConvertedNumber(int inputNumber){
      return convertedNumber;
    }

    public void setConvertedNumber(int inputNumber) {
      this.convertedNumber = convertedNumber;
    }

    public String toString() {
      return "Input: " + inputNumber + " | Converted: " + convertedNumber;
    }
}

