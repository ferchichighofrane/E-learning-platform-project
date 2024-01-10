package swing.table;

import model.ModelStudent;
import model.userEntity;

public interface EventAction {

    public void delete(ModelStudent student);

    public void update(ModelStudent student);
}
