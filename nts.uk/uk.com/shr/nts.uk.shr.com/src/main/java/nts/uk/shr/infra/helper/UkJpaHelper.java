package nts.uk.shr.infra.helper;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.helper.JPAHelper;
import nts.arc.layer.ws.json.JacksonUtil;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Stateless
public class UkJpaHelper implements JPAHelper {

	@Override
	public boolean makeDirty(Object entity) {
		try {
			if(entity instanceof UkJpaEntity){
				return makeDirtyDetail((UkJpaEntity) entity);
			} else if (entity instanceof Collection<?>) {
				boolean flag = false;
				for (Object e : (Collection<?>) entity){
					flag = flag && makeDirty(e);
				}
				return flag;
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private <T extends UkJpaEntity> boolean makeDirtyDetail(T entity) throws IllegalArgumentException, IllegalAccessException {
		boolean flag = false;
		entity.setUpdDate(GeneralDateTime.now());
		List<Field> fields = JacksonUtil.getAllFieldFromClass(entity.getClass());
		for(Field field : fields){
			Object fieldValue = field.get(entity);
			flag = flag && makeDirty(fieldValue);
		}
		return flag;
	}

}
