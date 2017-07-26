package nts.uk.shr.com.program;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ProgramsManager
 * 
 */
public class ProgramsManager {

	/**
	 * Ccg001d
	 */
	public static final Program Ccg001d = new Program(WebAppId.AT, ProgramIdConsts.Ccg001d, "PgName_Ccg001d",
			"/view/ccg/001/d/index.xhtml");
	/**
	 * CCG008A
	 */
	public static final Program CCG008A = new Program(WebAppId.COM, ProgramIdConsts.CCG008A, "PgName_CCG008A",
			"/view/ccg/008/a/index.xhtml");
	/**
	 * CCG008B
	 */
	public static final Program CCG008B = new Program(WebAppId.COM, ProgramIdConsts.CCG008B, "PgName_CCG008B",
			"/view/ccg/008/b/index.xhtml");
	/**
	 * CCG008C
	 */
	public static final Program CCG008C = new Program(WebAppId.COM, ProgramIdConsts.CCG008C, "PgName_CCG008C",
			"/view/ccg/008/c/index.xhtml");
	/**
	 * CCG008D
	 */
	public static final Program CCG008D = new Program(WebAppId.COM, ProgramIdConsts.CCG008D, "PgName_CCG008D",
			"/view/ccg/008/d/index.xhtml");
	/**
	 * CCG013A
	 */
	public static final Program CCG013A = new Program(WebAppId.COM, ProgramIdConsts.CCG013A, "PgName_CCG013A",
			"/view/ccg/013/a/index.xhtml");
	/**
	 * CCG013B
	 */
	public static final Program CCG013B = new Program(WebAppId.COM, ProgramIdConsts.CCG013B, "PgName_CCG013B",
			"/view/ccg/013/b/index.xhtml");
	/**
	 * CCG013C
	 */
	public static final Program CCG013C = new Program(WebAppId.COM, ProgramIdConsts.CCG013C, "PgName_CCG013C",
			"/view/ccg/013/c/index.xhtml");
	/**
	 * CCG013D
	 */
	public static final Program CCG013D = new Program(WebAppId.COM, ProgramIdConsts.CCG013D, "PgName_CCG013D",
			"/view/ccg/013/d/index.xhtml");
	/**
	 * CCG013E
	 */
	public static final Program CCG013E = new Program(WebAppId.COM, ProgramIdConsts.CCG013E, "PgName_CCG013E",
			"/view/ccg/013/e/index.xhtml");
	/**
	 * CCG013F
	 */
	public static final Program CCG013F = new Program(WebAppId.COM, ProgramIdConsts.CCG013F, "PgName_CCG013F",
			"/view/ccg/013/f/index.xhtml");
	/**
	 * CCG013G
	 */
	public static final Program CCG013G = new Program(WebAppId.COM, ProgramIdConsts.CCG013G, "PgName_CCG013G",
			"/view/ccg/013/g/index.xhtml");
	/**
	 * CCG013I
	 */
	public static final Program CCG013I = new Program(WebAppId.COM, ProgramIdConsts.CCG013I, "PgName_CCG013I",
			"/view/ccg/013/i/index.xhtml");
	/**
	 * CCG013J
	 */
	public static final Program CCG013J = new Program(WebAppId.COM, ProgramIdConsts.CCG013J, "PgName_CCG013J",
			"/view/ccg/013/j/index.xhtml");
	/**
	 * CCG013K
	 */
	public static final Program CCG013K = new Program(WebAppId.COM, ProgramIdConsts.CCG013K, "PgName_CCG013K",
			"/view/ccg/013/k/index.xhtml");
	/**
	 * CCG015A
	 */
	public static final Program CCG015A = new Program(WebAppId.COM, ProgramIdConsts.CCG015A, "PgName_CCG015A",
			"/view/ccg/015/a/index.xhtml");

	/**
	 * CCG015B
	 */
	public static final Program CCG015B = new Program(WebAppId.COM, ProgramIdConsts.CCG015B, "PgName_CCG015B",
			"/view/ccg/015/b/index.xhtml");

	/**
	 * CCG014A
	 */
	public static final Program CCG014A = new Program(WebAppId.COM, ProgramIdConsts.CCG014A, "PgName_CCG014A",
			"/view/ccg/014/a/index.xhtml");

	/**
	 * CCG014B
	 */
	public static final Program CCG014B = new Program(WebAppId.COM, ProgramIdConsts.CCG014B, "PgName_CCG014B",
			"/view/ccg/014/b/index.xhtml");
	/**
	 * CCG018A
	 */
	public static final Program CCG018A = new Program(WebAppId.COM, ProgramIdConsts.CCG018A, "PgName_CCG018A",
			"/view/ccg/018/a/index.xhtml");
	/**
	 * CCG018B
	 */
	public static final Program CCG018B = new Program(WebAppId.COM, ProgramIdConsts.CCG018B, "PgName_CCG018B",
			"/view/ccg/018/b/index.xhtml");
	/**
	 * CCG030A
	 */
	public static final Program CCG030A = new Program(WebAppId.COM, ProgramIdConsts.CCG030A, "PgName_CCG030A",
			"/view/ccg/030/a/index.xhtml");

	/**
	 * CCG030B
	 */
	public static final Program CCG030B = new Program(WebAppId.COM, ProgramIdConsts.CCG030B, "PgName_CCG030B",
			"/view/ccg/030/b/index.xhtml");

	/**
	 * CCG031A
	 */
	public static final Program CCG031A = new Program(WebAppId.COM, ProgramIdConsts.CCG031A, "PgName_CCG031A",
			"/view/ccg/031/a/index.xhtml");
	/**
	 * CCG031B
	 */
	public static final Program CCG031B = new Program(WebAppId.COM, ProgramIdConsts.CCG031B, "PgName_CCG031B",
			"/view/ccg/031/b/index.xhtml");
	/**
	 * CCG031C
	 */
	public static final Program CCG031C = new Program(WebAppId.COM, ProgramIdConsts.CCG031C, "PgName_CCG031C",
			"/view/ccg/031/c/index.xhtml");
	/**
	 * CMM044A
	 */
	public static final Program CMM044A = new Program(WebAppId.COM, ProgramIdConsts.CMM044A, "PgName_CMM044A",
			"/view/cmm/044/a/index.xhtml");
	/**
	 * KMK011A
	 */
	public static final Program KMK011A = new Program(WebAppId.AT, ProgramIdConsts.KMK011A, "PgName_KMK011A",
			"/view/kmk/011/a/index.xhtml");
	/**
	 * KMK011B
	 */
	public static final Program KMK011B = new Program(WebAppId.AT, ProgramIdConsts.KMK011B, "PgName_KMK011B",
			"/view/kmk/011/b/index.xhtml");
	/**
	 * KML001A
	 */
	public static final Program KML001A = new Program(WebAppId.AT, ProgramIdConsts.KML001A, "PgName_KML001A",
			"/view/kml/001/a/index.xhtml");
	/**
	 * KML001B
	 */
	public static final Program KML001B = new Program(WebAppId.AT, ProgramIdConsts.KML001B, "PgName_KML001B",
			"/view/kml/001/b/index.xhtml");
	/**
	 * KML001C
	 */
	public static final Program KML001C = new Program(WebAppId.AT, ProgramIdConsts.KML001C, "PgName_KML001C",
			"/view/kml/001/c/index.xhtml");
	/**
	 * KML001D
	 */
	public static final Program KML001D = new Program(WebAppId.AT, ProgramIdConsts.KML001D, "PgName_KML001D",
			"/view/kml/001/d/index.xhtml");
	/**
	 * KDL024A
	 */
	public static final Program KDL024A = new Program(WebAppId.AT, ProgramIdConsts.KDL024A, "PgName_KDL024A",
			"/view/kdl/024/a/index.xhtml");

	/**
	 * KDL014A
	 */
	public static final Program KDL014A = new Program(WebAppId.AT, ProgramIdConsts.KDL014A, "PgName_KDL014A",
			"/view/kdl/014/a/index.xhtml");

	/**
	 * KDL014B
	 */
	public static final Program KDL014B = new Program(WebAppId.AT, ProgramIdConsts.KDL014B, "PgName_KDL014B",
			"/view/kdl/014/b/index.xhtml");

	/**
	 * KSM002A
	 */
	public static final Program KSM002A = new Program(WebAppId.AT, ProgramIdConsts.KSM002A, "PgName_KSM002A",
			"/view/ksm/002/a/index.xhtml");

	/**
	 * KSM002B
	 */
	public static final Program KSM002B = new Program(WebAppId.AT, ProgramIdConsts.KSM002B, "PgName_KSM002B",
			"/view/ksm/002/b/index.xhtml");

	/**
	 * KSM002C
	 */
	public static final Program KSM002C = new Program(WebAppId.AT, ProgramIdConsts.KSM002C, "PgName_KSM002C",
			"/view/ksm/002/c/index.xhtml");

	/**
	 * KSM002D
	 */
	public static final Program KSM002D = new Program(WebAppId.AT, ProgramIdConsts.KSM002D, "PgName_KSM002D",
			"/view/ksm/002/d/index.xhtml");

	/**
	 * KSM004A
	 */
	public static final Program KSM004A = new Program(WebAppId.AT, ProgramIdConsts.KSM004A, "PgName_KSM004A",
			"/view/ksm/004/a/index.xhtml");

	/**
	 * KSM004C
	 */
	public static final Program KSM004C = new Program(WebAppId.AT, ProgramIdConsts.KSM004C, "PgName_KSM004C",
			"/view/ksm/004/c/index.xhtml");

	/**
	 * KSM004D
	 */
	public static final Program KSM004D = new Program(WebAppId.AT, ProgramIdConsts.KSM004D, "PgName_KSM004D",
			"/view/ksm/004/d/index.xhtml");
	/**
	 * KSU001A
	 */
	public static final Program KSU001A = new Program(WebAppId.AT, ProgramIdConsts.KSU001A, "PgName_KSU001A",
			"/view/ksu/001/a/index.xhtml");

	// TODO: Define new programs here.

	/**
	 * All programs map.
	 */
	private static final Map<WebAppId, List<Program>> PROGRAMS;

	static {
		Function<Field, Optional<Program>> pg = f -> {
			try {
				if (Modifier.isStatic(f.getModifiers()))
					return Optional.of((Program) f.get(null));
				else
					return Optional.empty();
			} catch (IllegalArgumentException | IllegalAccessException ex) {
				throw new RuntimeException(ex);
			}
		};
		PROGRAMS = Arrays.asList(ProgramsManager.class.getFields()).stream().filter(f -> f.getType() == Program.class)
				.map(f -> pg.apply(f).get()).collect(Collectors.groupingBy(Program::getAppId));
	}

	/**
	 * Finds program.
	 * 
	 * @param appId
	 *            appId.
	 * @param path
	 *            program path.
	 * @return optional program.
	 */
	public static Optional<Program> find(WebAppId appId, String path) {
		if (appId == null || path == null)
			return Optional.empty();
		Optional<Set<Program>> programsOpt = getSet(appId);
		if (!programsOpt.isPresent())
			return Optional.empty();
		return programsOpt.get().stream().filter(a -> path.equals(a.getPPath())).findFirst();
	}

	/**
	 * Finds program Id.
	 * 
	 * @param appId
	 *            appId.
	 * @param path
	 *            path.
	 * @return optional program Id.
	 */
	public static Optional<String> idOf(WebAppId appId, String path) {
		return Optional.ofNullable(find(appId, path).orElse(new Program()).getPId());
	}

	/**
	 * Finds program name.
	 * 
	 * @param appId
	 *            appId.
	 * @param path
	 *            path.
	 * @return optional program name.
	 */
	public static Optional<String> nameOf(WebAppId appId, String path) {
		return Optional.ofNullable(find(appId, path).orElse(new Program()).getPName());
	}

	/**
	 * Gets predefined set.
	 * 
	 * @param appId
	 *            appId.
	 * @return optional program set.
	 */
	private static Optional<Set<Program>> getSet(WebAppId appId) {
		List<Program> programs = PROGRAMS.get(appId);
		if (programs == null || programs.size() == 0)
			return Optional.empty();
		return Optional.of(programs.stream().collect(Collectors.toSet()));
	}
}
