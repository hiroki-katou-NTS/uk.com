package nts.sample.parallel;

import javax.ejb.Stateless;

import lombok.extern.slf4j.Slf4j;

@Stateless
@Slf4j
public class CdiInjectTestImpl implements CdiInjectTest {

	@Override
	public void run() {
		log.info("CdiInjectTestImpl!!!");
	}

}
