import java.util.ArrayList;
import java.util.List;

/**
 * Created by elena on 06.04.17.
 */
public class Field {
    private List<List<Integer>> field;
    private Integer fieldId;
    private Integer parentId;
    private Integer funcG;
    private Integer funcH;

    public List<List<Integer>> getField(){ return field;}
    public Integer getFuncG() { return funcG; }
    public Integer getFuncH() { return funcH; }
    public Integer getFieldId() { return fieldId;}
    public Integer getParentId() { return parentId;}

    public Field(List<List<Integer>> field, Integer fieldId, Integer parentId){
        this.field = field;
        this.fieldId = fieldId;
        this.parentId = parentId;
        funcH = 0;
        funcG = 0;
    }


    public Field(Field fi, Field finalField){
        field = new ArrayList<>();
        for ( int i = 0; i < fi.getField().size(); i++){
            List<Integer> temp = new ArrayList<>();
            for ( int j = 0; j < fi.getField().size(); j++){
                temp.add(fi.getField().get(i).get(j));
            }
            field.add(temp);
        }
        this.fieldId = fi.getFieldId();
        this.parentId = fi.getParentId();
        this.funcG = fi.getFuncG();
        this.funcH = HeuristicFunction(finalField);
    }

    public void SetParentId(Integer id){ parentId = id; }

    public void SetId(Integer id){ fieldId = id; }

    public void SetFuncG(Integer n){ funcG = n; }

    private Integer HeuristicFunction(Field finalField){
        int result = 0;
        for (int i = 0; i < field.size(); i++) {
            for ( int j = 0; j < field.size(); j++) {
                if (field.get(i).get(j) != finalField.getField().get(i).get(j))
                {
                    result ++;
                }
            }
        }
        for (int i = 0; i < field.size(); i++){
            for (int j = 0; j < field.size(); j++ ){
                if(i < 3 && j < 3 & field.get(i).get(j) == 1){
                    result += (Math.abs(i - 3) + Math.abs(j - 2));
                }
                if(i > 1 && j > 1 && field.get(i).get(j) == 2){
                    result += (Math.abs(i - 1) + Math.abs(j - 2));
                }
            }
        }
        return result;
    }
}
