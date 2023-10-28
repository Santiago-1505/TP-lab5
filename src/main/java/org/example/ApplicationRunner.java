package org.example;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

import org.example.exception.GradeNotFoundException;
import org.example.model.Grade;
import org.example.repository.GradeInMemoryRepositoryImpl;
import org.example.repository.GradeUsingFileRepositoryImpl;
import org.example.service.AcademicRecordService;
import org.example.service.AcademicRecordServiceImpl;


public class ApplicationRunner {
  public static void main(String[] args) {

    AcademicRecordService academicRecordService =
            new AcademicRecordServiceImpl(new GradeUsingFileRepositoryImpl());

    //Disclaimer: Este metodo sigue el principio SOLID de single responsibility
    mostrarNotas("Notas iniciales", academicRecordService.listAllGrades());

    Grade grade = new Grade("PARCIAL", 4.5D, LocalDate.now());
    academicRecordService.addGrade(grade);

    mostrarNotas("Notas después de adicionar una nueva",academicRecordService.listAllGrades());

    mostrarResultado("Suma de número calificaciones", academicRecordService.sumNumberOfGrades());
    mostrarResultado("Promedio", academicRecordService.calculateAverage());

    String nombreProyecto = "Unidad 10";
    busquedaNota(academicRecordService, nombreProyecto);
  }
  private static void mostrarNotas (String mensaje, List<Grade>gradeList){
      System.out.println(mensaje);
      gradeList.forEach(System.out::println);
  }
  private static void mostrarResultado (String mensaje, Object resultado){
      System.out.println(MessageFormat.format("{0}: {1}", mensaje, resultado));
  }

  private static void busquedaNota (AcademicRecordService academicRecordService, String nombreProyecto){
    try {
      Grade grade = academicRecordService.getGrade( nombreProyecto );
      System.out.println("Nota del proyecto '" + nombreProyecto + "': " + grade);
    }
    catch (GradeNotFoundException e) {
      System.out.println(MessageFormat.format("No se encontró una nota para la unidad {0} ", nombreProyecto));
    }

  }
}
