/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
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
	 * CPS007B
	 */
	public static final Program CPS007B = new Program(WebAppId.COM, ProgramIdConsts.CPS007B, "PgName_CPS007B",
			"/view/cps/007/b/index.xhtml");
	/**
	 * CPS008D
	 */
	public static final Program CPS008D = new Program(WebAppId.COM, ProgramIdConsts.CPS008D, "PgName_CPS008D",
			"/view/cps/007/b/index.xhtml");
	/**
	 * CPS008C
	 */
	public static final Program CPS008C = new Program(WebAppId.COM, ProgramIdConsts.CPS008C, "PgName_CPS008C",
			"/view/cps/007/b/index.xhtml");
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
	 * KDW009A
	 */
	public static final Program KDW009A = new Program(WebAppId.AT, ProgramIdConsts.KDW009A, "PgName_KDW009A",
			"/view/kdw/009/a/index.xhtml");
	
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

	/** The Constant KDL003. */
	public static final Program KDL003 = new Program(WebAppId.AT, ProgramIdConsts.KDL003, "PgName_KDL003",
			"/view/kdl/003/a/index.xhtml");
	
	/** The Constant KSM006. */
	public static final Program KSM006 = new Program(WebAppId.AT, ProgramIdConsts.KSM006, "PgName_KSM006",
			"/view/ksm/003/a/index.xhtml");
	
	/** The Constant KSM003. */
	public static final Program KSM003 = new Program(WebAppId.AT, ProgramIdConsts.KSM003, "PgName_KSM003",
			"/view/ksm/003/a/index.xhtml");
	
	/** The Constant KSM005A. */
	public static final Program KSM005A = new Program(WebAppId.AT, ProgramIdConsts.KSM005A, "PgName_KSM005A",
			"/view/ksm/005/a/index.xhtml");
	
	/** The Constant KSM005B. */
	public static final Program KSM005B = new Program(WebAppId.AT, ProgramIdConsts.KSM005B, "PgName_KSM005B",
			"/view/ksm/005/b/index.xhtml");
	
	/** The Constant KSM005C. */
	public static final Program KSM005C = new Program(WebAppId.AT, ProgramIdConsts.KSM005C, "PgName_KSM005C",
			"/view/ksm/005/c/index.xhtml");
	
	/** The Constant KSM005E. */
	public static final Program KSM005E = new Program(WebAppId.AT, ProgramIdConsts.KSM005E, "PgName_KSM005E",
			"/view/ksm/005/e/index.xhtml");
	
	/** The Constant KSM005F. */
	public static final Program KSM005F = new Program(WebAppId.AT, ProgramIdConsts.KSM005F, "PgName_KSM005F",
			"/view/ksm/005/f/index.xhtml");
	
	/** The Constant KDL023. */
	public static final Program KDL023A = new Program(WebAppId.AT, ProgramIdConsts.KDL023A, "PgName_KDL023A",
			"/view/kdl/023/a/index.xhtml");
	
	public static final Program KDL023B = new Program(WebAppId.AT, ProgramIdConsts.KDL023B, "PgName_KDL023B",
			"/view/kdl/023/b/index.xhtml");
	
	/** The Constant KSM001. */
	public static final Program KSM001 = new Program(WebAppId.AT, ProgramIdConsts.KSM001, "PgName_KSM001",
			"/view/ksm/001/a/index.xhtml");
	
	/** The Constant KMK009. */
	public static final Program KMK009 = new Program(WebAppId.AT, ProgramIdConsts.KMK009, "PgName_KMK009",
			"/view/kmk/009/a/index.xhtml");
	
	/** The Constant KSU006. */
	public static final Program KSU006 = new Program(WebAppId.AT, ProgramIdConsts.KSU006, "PgName_KSU006",
			"/view/ksu/006/a/index.xhtml");
	
	/** The Constant CCG007. */
	public static final Program CCG007 = new Program(WebAppId.COM, ProgramIdConsts.CCG007, "PgName_CCG007",
			"/view/ccg/007/a/index.xhtml");
	
	/** The Constant CCG001. */
	public static final Program CCG001 = new Program(WebAppId.COM, ProgramIdConsts.CCG001, "PgName_CCG001",
			"/view/ccg/001/a/index.xhtml");
	
	/** The Constant KMK004A. */
	public static final Program KMK004A  = new Program(WebAppId.AT, ProgramIdConsts.KMK004A, "PgName_KMK004A",
			"/view/kmk/004/a/index.xhtml");
	
	/** The Constant KMK004E. */
	public static final Program KMK004E  = new Program(WebAppId.AT, ProgramIdConsts.KMK004E, "PgName_KMK004E",
			"/view/kmk/004/e/index.xhtml");
	
	/** The Constant KMK012A. */
	public static final Program KMK012A  = new Program(WebAppId.AT, ProgramIdConsts.KMK012A, "PgName_KMK012A",
			"/view/kmk/012/a/index.xhtml");
	
	/** The Constant KMK012D. */
	public static final Program KMK012D  = new Program(WebAppId.AT, ProgramIdConsts.KMK012D, "PgName_KMK012D",
			"/view/kmk/012/d/index.xhtml");
	
	/** The Constant KMF001A. */
	public static final Program KMF001A  = new Program(WebAppId.AT, ProgramIdConsts.KMF001A, "PgName_KMF001A",
			"/view/kmk/001/a/index.xhtml");
	
	/** The Constant KMF001B. */
	public static final Program KMF001B  = new Program(WebAppId.AT, ProgramIdConsts.KMF001B, "PgName_KMF001B",
			"/view/kmk/001/b/index.xhtml");
	
	/** The Constant KMF001C. */
	public static final Program KMF001C  = new Program(WebAppId.AT, ProgramIdConsts.KMF001C, "PgName_KMF001C",
			"/view/kmk/001/c/index.xhtml");
	
	/** The Constant KMF001D. */
	public static final Program KMF001D  = new Program(WebAppId.AT, ProgramIdConsts.KMF001D, "PgName_KMF001D",
			"/view/kmk/001/d/index.xhtml");
	
	/** The Constant KMF001F. */
	public static final Program KMF001F  = new Program(WebAppId.AT, ProgramIdConsts.KMF001F, "PgName_KMF001F",
			"/view/kmk/001/f/index.xhtml");
	
	/** The Constant KMF001H. */
	public static final Program KMF001H  = new Program(WebAppId.AT, ProgramIdConsts.KMF001H, "PgName_KMF001H",
			"/view/kmk/001/h/index.xhtml");
	
	/** The Constant KMF001J. */
	public static final Program KMF001J  = new Program(WebAppId.AT, ProgramIdConsts.KMF001J, "PgName_KMF001J",
			"/view/kmk/001/j/index.xhtml");
	
	/** The Constant KMF001L. */
	public static final Program KMF001L  = new Program(WebAppId.AT, ProgramIdConsts.KMF001L, "PgName_KMF001L",
			"/view/kmk/001/l/index.xhtml");
	
	/**
	 * KMF003A
	 */
	public static final Program KMF003A  = new Program(WebAppId.AT, ProgramIdConsts.KMF003A, "PgName_KMF003A",
			"/view/kmf/003/a/index.xhtml");
		
	/**
	 * KMF003B
	 */
	public static final Program KMF003B  = new Program(WebAppId.AT, ProgramIdConsts.KMF003B, "PgName_KMF003B",
			"/view/kmf/003/b/index.xhtml");
	
	/**
	 * KMF004A
	 */
	public static final Program KMF004A  = new Program(WebAppId.AT, ProgramIdConsts.KMF004A, "PgName_KMF004A",
			"/view/kmf/004/a/index.xhtml");
	
	/**
	 * KMF004B
	 */
	public static final Program KMF004B  = new Program(WebAppId.AT, ProgramIdConsts.KMF004B, "PgName_KMF004B",
			"/view/kmf/004/b/index.xhtml");
	
	/**
	 * KMF004C
	 */
	public static final Program KMF004C  = new Program(WebAppId.AT, ProgramIdConsts.KMF004C, "PgName_KMF004C",
			"/view/kmf/004/c/index.xhtml");
	
	/**
	 * KMF004D
	 */
	public static final Program KMF004D  = new Program(WebAppId.AT, ProgramIdConsts.KMF004D, "PgName_KMF004D",
			"/view/kmf/004/d/index.xhtml");
	
	/**
	 * KMF004E
	 */
	public static final Program KMF004E  = new Program(WebAppId.AT, ProgramIdConsts.KMF004E, "PgName_KMF004E",
			"/view/kmf/004/e/index.xhtml");
	
	/**
	 * KMF004F
	 */
	public static final Program KMF004F  = new Program(WebAppId.AT, ProgramIdConsts.KMF004F, "PgName_KMF004F",
			"/view/kmf/004/f/index.xhtml");
	
	/**
	 * KMF004G
	 */
	public static final Program KMF004G  = new Program(WebAppId.AT, ProgramIdConsts.KMF004G, "PgName_KMF004G",
			"/view/kmf/004/g/index.xhtml");
	
	/**
	 * KMF004H
	 */
	public static final Program KMF004H  = new Program(WebAppId.AT, ProgramIdConsts.KMF004H, "PgName_KMF004H",
			"/view/kmf/004/h/index.xhtml");
	
	/**
	 * KMK007A
	 */
	public static final Program KMK007A  = new Program(WebAppId.AT, ProgramIdConsts.KMK007A, "PgName_KMK007A",
			"/view/kmk/007/a/index.xhtml");
	
	/**
	 * KMK007B
	 */
	public static final Program KMK007B  = new Program(WebAppId.AT, ProgramIdConsts.KMK007B, "PgName_KMK007B",
			"/view/kmk/007/b/index.xhtml");
	
	/**
	 * KMK007C
	 */
	public static final Program KMK007C  = new Program(WebAppId.AT, ProgramIdConsts.KMK007C, "PgName_KMK007C",
			"/view/kmk/007/c/index.xhtml");
	
	/**
	 * CAS001C
	 */
	public static final Program CAS001C  = new Program(WebAppId.AT, ProgramIdConsts.CAS001C, "PgName_CAS001C",
			"/view/cas/001/c/index.xhtml");
	
	/**
	 * CAS001D
	 */
	public static final Program CAS001D  = new Program(WebAppId.AT, ProgramIdConsts.CAS001D, "PgName_CAS001D",
			"/view/cas/001/d/index.xhtml");
	
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
