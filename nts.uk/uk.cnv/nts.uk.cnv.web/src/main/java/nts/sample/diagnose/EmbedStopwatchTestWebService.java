package nts.sample.diagnose;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.layer.app.command.JavaTypeResult;

@Produces(MediaType.APPLICATION_JSON)
@Path("/nts/test/diagnose/stopwatch/embed")
public class EmbedStopwatchTestWebService {

	@GET
	@Path("test1")
	public JavaTypeResult<String> test1() {
		
		Require require = EmbedStopwatch.embed(new RequireImpl());
		
		require.foo(100);
		
		SampleDomainService.sample(require, 10).run();
		SampleDomainService.sample2(require, 10);
		
		return new JavaTypeResult<>("ok");
	}

	@GET
	@Path("test2")
	public JavaTypeResult<String> test2() {
		
		Require require = EmbedStopwatch.embed(new RequireImpl());
		
		for (int i = 0; i < 10; i++) {
			SampleDomainService.sample(require, i).run();
			SampleDomainService.sample2(require, i);
		}
		
		return new JavaTypeResult<>("ok");
	}
	
	public static interface LocalRequire {
		int foo(int a);
	}
	
	static interface Require extends LocalRequire, SampleDomainService.Require, SampleDomainService.Require2 {
	}
	
	static class RequireImpl implements Require {

		@Override
		public int hoge(int a, int b) {
			sleep(100);
			return 0;
		}

		@Override
		public int fuga(int a, int b) {
			sleep(100);
			return 0;
		}

		@Override
		public void save(int a) {
			sleep(100);
		}
		
		@SneakyThrows 
		private static void sleep(int ms) {
			Thread.sleep(ms);
		}

		@Override
		public int piyo(int a) {
			return a * 2;
		}

		@Override
		public int foo(int a) {
			return 100;
		}
		
	}
}
