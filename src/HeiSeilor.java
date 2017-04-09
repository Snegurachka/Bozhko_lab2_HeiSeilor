import java.util.ArrayList;
import java.util.List;

/**
 * Created by elena on 06.04.17.
 */
/*
 _____
|1 1 1|
|1 1 1|_____
|1 1 0 2 2 2|
 -----|2 2 2|
      |2 2 2|
       -----
 Загадка старого моряка
 Разместить все черные фишки (2) в верхней секции, а все
 белые (1) - в нижней.
 Допустимые ходы: по горизонтали или вертикали на соседнюю
 пустую клетку или прыжок через соседнюю занятую клетку
 (любую) на свободное поле.

 */
public class HeiSeilor {
    private Field fieldStart;
    private Field fieldEnd;
    private List<Field> listOPen;
    private List<Field> listClose;
    private List<Field> treeField;

    public List<Field> getTreeField(){ return treeField;}

    public HeiSeilor(){
        fieldStart = CreateStartField();
        fieldEnd = CreateEndField();
        listOPen = new ArrayList<>();
        listClose = new ArrayList<>();
        treeField = new ArrayList<>();
    }

    public boolean Resolve(){
        listOPen.add(fieldStart);
        boolean result = false;
        int key = 0;

        do {
            //проверка является ли первая вершина из open терминальной
            if (CompareField(listOPen.get(0), fieldEnd) && listOPen.get(0).getFuncG() < 48) {
                listClose.add(listOPen.get(0));
                listOPen.remove(0);
                result = true;
                break;
            }
            //находится ли первая вершина из Open в списке Close, если нет то закрываем открытую вершину
            for (int i = 0; i < listClose.size(); i++) {
                if (CompareField(listOPen.get(0), listClose.get(i)) && listOPen.get(0).getFuncG() < 48) {
                    key = 1;
                    listOPen.remove(0);
                    break;
                }
            }
            if (key == 1) {
                key = 0;
                continue;
            } else {
                listClose.add(listOPen.get(0));
                listOPen.remove(0);
            }
            //все возможные комбинации генерируем и записываем в Open
            NewFieldGeneration(listClose.get(listClose.size() - 1));
            MinGHField();
        } while (listOPen.size()!=0);

        treeField = GetTree(listClose.get(listClose.size() - 1));
        return result;
    }

    private List<Field> GetTree(Field resultField){
        List<Field> treeEnd = new ArrayList<>();
        treeEnd.add(resultField);
        int parentId = resultField.getParentId();
        for(int i = resultField.getFuncG() - 1; i >= 0; i--){
            for(int k = 0; k < listClose.size(); k++){
                if(listClose.get(k).getFuncG() == i && listClose.get(k).getFieldId() == parentId){
                    treeEnd.add(listClose.get(k));
                    parentId = listClose.get(k).getParentId();
                    break;
                }
            }
        }
        return treeEnd;
    }

    private void NewFieldGeneration(Field field){
        int id = 0;
        for ( int i = 0; i < listOPen.size(); ++i)
        {
            if (field.getFuncG() + 1 == listOPen.get(i).getFuncG() && listOPen.get(i).getFieldId() > id)
            {
                id = listOPen.get(i).getFieldId() + 1;
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (field.getField().get(i).get(j) == 1) {
                    if (i + 1 < 5 && field.getField().get(i + 1).get(j) == 0){
                        if (NewStep(field, i, j, i + 1, j)){
                            listOPen.get(listOPen.size() - 1).SetId(id);
                            id++;
                        }
                    }
                    if (i + 2 < 5 && field.getField().get(i + 2).get(j) == 0){
                        if (NewStep(field, i, j, i + 2, j)){
                            listOPen.get(listOPen.size() - 1).SetId(id);
                            id++;
                        }
                    }
                    if (j + 1 < 5 && field.getField().get(i).get(j + 1) == 0){
                        if (NewStep(field, i, j, i, j + 1)){
                            listOPen.get(listOPen.size() - 1).SetId(id);
                            id++;
                        }
                    }
                    if (j + 2 < 5 && field.getField().get(i).get(j + 2) == 0){
                        if (NewStep(field, i, j, i, j + 2)){
                            listOPen.get(listOPen.size() - 1).SetId(id);
                            id++;
                        }
                    }
                }

                if (field.getField().get(i).get(j) == 2) {
                    if (i - 1 >= 0 && field.getField().get(i - 1).get(j) == 0){
                        if (NewStep(field, i, j, i - 1, j)){
                            listOPen.get(listOPen.size() - 1).SetId(id);
                            id++;
                        }
                    }
                    if (i - 2 >= 0 && field.getField().get(i - 2).get(j) == 0){
                        if (NewStep(field, i, j, i - 2, j)){
                            listOPen.get(listOPen.size() - 1).SetId(id);
                            id++;
                        }
                    }
                    if (j - 1 >= 0 && field.getField().get(i).get(j - 1) == 0){
                        if (NewStep(field, i, j, i, j - 1)){
                            listOPen.get(listOPen.size() - 1).SetId(id);
                            id++;
                        }
                    }
                    if (j - 2 >= 0 && field.getField().get(i).get(j - 2) == 0){
                        if (NewStep(field, i, j, i, j - 2)){
                            listOPen.get(listOPen.size() - 1).SetId(id);
                            id++;
                        }
                    }
                }
            }
        }
    }


    private boolean NewStep(Field field, int iOld, int jOld, int iNew, int jNew){
        Field tempField = new Field(field, fieldEnd);
        Integer tempPos;
        tempPos = tempField.getField().get(iNew).get(jNew);
        tempField.getField().get(iNew).set(jNew, tempField.getField().get(iOld).get(jOld));
        tempField.getField().get(iOld).set(jOld, tempPos);

        for( int i = 0; i < listOPen.size(); i++){
            if(CompareField(listOPen.get(i), tempField)){
                return false;
            }
        }
        for (int i = 0; i < listClose.size(); i++){
            if(CompareField(listClose.get(i), tempField)){
                return false;
            }
        }
        listOPen.add(tempField);
        listOPen.get(listOPen.size() - 1).SetFuncG(tempField.getFuncG() + 1);
        listOPen.get(listOPen.size() - 1).SetParentId(tempField.getFieldId());
        return true;
    }

    private boolean CompareField(Field one, Field two){
        for (int i = 0; i < one.getField().size(); i++) {
            for (int j = 0; j < one.getField().size(); j++){
                if(one.getField().get(i).get(j) != two.getField().get(i).get(j)){
                    return false;
                }
            }
        }
        return true;
    }

    public Field CreateStartField(){
        List<List<Integer>> field = new ArrayList<>();
        int N = 5;
        for (int i = 0; i < N; i++){
            List<Integer> tempString = new ArrayList<>();
            for (int j = 0; j < N; j++){
                tempString.add(8);
            }
            field.add(tempString);
        }

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                if( i < 3 && j < 3){
                    field.get(i).set(j, 1);
                    continue;
                }
                if( i > 1 && j > 1)
                    field.get(i).set(j, 2);
            }
        }
        field.get(2).set(2, 0);
        Field fieldStart = new Field(field, 0, 0);
        return fieldStart;
    }

    public Field CreateEndField(){
        List<List<Integer>> field = new ArrayList<>();
        int N = 5;

        for (int i = 0; i < N; i++){
            List<Integer> tempString = new ArrayList<>();
            for (int j = 0; j < N; j++){
                tempString.add(8);
            }
            field.add(tempString);
        }

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                if( i < 3 && j < 3){
                    field.get(i).set(j, 2);
                }
                if( i > 1 && j > 1)
                    field.get(i).set(j, 1);
            }
        }
        field.get(2).set(2, 0);
        Field fieldStart = new Field(field, 0, 0);
        return fieldStart;

    }

    public void PrintField(Field field){
        for (int i = 0; i < field.getField().size(); i++){
            for (int j = 0; j < field.getField().size(); j++){
                System.out.print(field.getField().get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public void PrintFieldList(List<Field> fieldList){
        for(int k = 0; k < fieldList.size(); k++){
            System.out.println("Номер хода " + k);
            PrintField(fieldList.get(k));
            System.out.println();
        }
    }

    private void MinGHField(){
        Integer maxG = listOPen.get(0).getFuncG();
        Integer minGH = listOPen.get(0).getFuncH() + listOPen.get(0).getFuncG();
        Integer minPosition = 0;
        for(int i = 0; i < listOPen.size(); i++){
            if (listOPen.get(i).getFuncG() + listOPen.get(i).getFuncH() < minGH){
                minGH = listOPen.get(i).getFuncG() + listOPen.get(i).getFuncH();
                maxG = listOPen.get(i).getFuncG();
                minPosition = i;
            } else if (listOPen.get(i).getFuncG() + listOPen.get(i).getFuncH() == minGH){
                if (listOPen.get(i).getFuncG() > maxG){
                    minGH = listOPen.get(i).getFuncG() + listOPen.get(i).getFuncH();
                    maxG = listOPen.get(i).getFuncG();
                    minPosition = i;
                }
            }
        }
        if (minPosition > 0){
            Field fieldTemp = listOPen.get(0);
            listOPen.set(0, listOPen.get(minPosition));
            listOPen.set(minPosition, fieldTemp);
        }
    }
}
