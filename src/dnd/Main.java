package dnd;
import java.util.ArrayList;
import dnd.models.ChamberShape;
import java.util.Random;
import java.io.Serializable;


public class Main implements Serializable {

    private ArrayList<Chamber> chamberList;
    private ArrayList<Passage> passageList;

    public void run() {
        Chamber newChamberOne = new Chamber();
        Chamber newChamberTwo = new Chamber();
        Chamber newChamberThree = new Chamber();
        Chamber newChamberFour = new Chamber();
        Chamber newChamberFive = new Chamber();
        chamberList = new ArrayList<Chamber>();
        passageList = new ArrayList<Passage>();
        Passage passageOne = new Passage();
        PassageSection newPassageSection = new PassageSection("Monster");
        PassageSection newPassageSection1 = new PassageSection();
        PassageSection newPassageSection2 = new PassageSection("Door");
        passageOne.addPassageSection(newPassageSection);
        passageOne.addPassageSection(newPassageSection1);
        passageOne.addPassageSection(newPassageSection2);
        Passage passageTwo = new Passage();
        PassageSection newPassageSection3 = new PassageSection("Monster");
        PassageSection newPassageSection4 = new PassageSection();
        PassageSection newPassageSection12 = new PassageSection("Monster");
        passageTwo.addPassageSection(newPassageSection3);
        passageTwo.addPassageSection(newPassageSection4);
        passageTwo.addPassageSection(newPassageSection12);
        Passage passageThree = new Passage();
        PassageSection newPassageSection5 = new PassageSection("archway");
        passageThree.addPassageSection(newPassageSection5);
        Passage passageFour= new Passage();
        PassageSection newPassageSection6 = new PassageSection();
        PassageSection newPassageSection7 = new PassageSection("Monster");
        PassageSection newPassageSection8 = new PassageSection("Door");
        PassageSection newPassageSection9 = new PassageSection("Monster");
        passageFour.addPassageSection(newPassageSection6);
        passageFour.addPassageSection(newPassageSection7);
        passageFour.addPassageSection(newPassageSection8);
        passageFour.addPassageSection(newPassageSection9);
        Passage passageFive = new Passage();
        PassageSection newPassageSection10 = new PassageSection();
        PassageSection newPassageSection11 = new PassageSection("archway");
        passageFive.addPassageSection(newPassageSection10);
        passageFive.addPassageSection(newPassageSection11);
        
        newChamberOne.setShape(makeShape());
        newChamberTwo.setShape(makeShape());
        newChamberThree.setShape(makeShape());
        newChamberFour.setShape(makeShape());
        newChamberFive.setShape(makeShape());
        chamberList.add(0, newChamberOne);
        chamberList.add(1, newChamberTwo);
        chamberList.add(2, newChamberThree);
        chamberList.add(3, newChamberFour);
        chamberList.add(4, newChamberFive);
        passageList.add(0, passageOne);
        passageList.add(1, passageTwo);
        passageList.add(2, passageThree);
        passageList.add(3, passageFour);
        passageList.add(4, passageFive);
    }


    private ChamberShape makeShape() {
        ChamberShape newShape = ChamberShape.selectChamberShape(randomNumber(20, 1));
        newShape.setNumExits(2);
        return newShape;
    }

    private int randomNumber(int max, int min) {
		Random number = new Random();
		int result = number.nextInt(max - min + 1) + min;
		return result;
	}

    public ArrayList<Chamber> getChamberList() {
        ArrayList <Chamber> returnList = new ArrayList<Chamber>();
        returnList = chamberList;
        return returnList;
    }

    public ArrayList<Passage> getPassageList() {
        ArrayList<Passage> returnList = new ArrayList<Passage>();
        returnList = passageList;
        return returnList;
    }

}