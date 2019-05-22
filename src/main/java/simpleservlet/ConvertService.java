package simpleservlet;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ConvertService {

    @PersistenceContext(unitName = "webapp")
    private EntityManager entityManager;

    private Convert convert;

    public void inputData(int inputNumber) { // skriver i databasen
      int converted = convertTo(inputNumber);
        Convert convert = new Convert(inputNumber, converted);
        entityManager.persist(convert);
    }

    public int convertTo(int inputNumber) {
      double fa = Double.valueOf(inputNumber) * 1.8000 + 32.000;
      int faInt = (int)fa;
      return faInt;
    }

    public List<Convert> findAll() {
      Query q = entityManager.createQuery("select convert from Convert convert");
  		List<Convert> someList = q.getResultList();

      return someList;
    }
}

