package com.koley.musrights.datasets;

import java.util.ArrayList;
import java.util.List;

public class UserFullNamesDataset{
  public List<String> userFullNames = new ArrayList<>();
  public int size = 0;
  public UserFullNamesDataset(){
    addData("Андрей Андреев");
    addData("Сергей Сергеев");
    addData("Кирилл Кириллов");
    addData("Иван Иванов");
    addData("Петр Петров");
    addData("Дмитрий Дмитров");
    addData("Алексей Алексеев");
    addData("Евгений Евгеньев");
    addData("Михаил Михайлов");
    addData("Антон Антонов");
    addData("Екатерина Андреева");
    addData("Алина Сергеева");
    addData("Мария Кириллова");
    addData("Марина Иванова");
    addData("Дарья Петрова");
    addData("Анна Дмитрова");
    addData("Валерия Алексеева");
    addData("Карина Евгеньева");
    addData("Элеонора Михайлова");
    addData("Юлия Антонова");
  }

  private void addData(String userFullName){
      userFullNames.add(userFullName);
    this.size++;
  }
}
