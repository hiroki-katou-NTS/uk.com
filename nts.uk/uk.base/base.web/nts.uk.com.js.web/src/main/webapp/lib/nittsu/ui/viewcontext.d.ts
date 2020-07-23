/// <reference path="../generic.d.ts/lodash.d.ts" />
/// <reference path="../generic.d.ts/jquery.d.ts" />
/// <reference path="../generic.d.ts/jqueryui.d.ts" />
/// <reference path="../generic.d.ts/moment.d.ts" />
/// <reference path="../generic.d.ts/knockout.d.ts" />
/// <reference path="../nts.uk.com.web.nittsu.bundles.d.ts" />

declare var __viewContext: ViewContext;
declare var systemLanguage: string;
declare var names: Names;
declare var messages: Messages;

interface ViewContext {
	rootPath: string;
	primitiveValueConstraints: { [key: string]: any };
	codeNames: { [key: string]: string };
	messages: { [key: string]: string };
	env: any;
	noHeader: boolean;

	title: string;
	transferred: nts.uk.util.optional.Optional<any>;

	ready: (callback: () => void) => void;
	bind: (viewModel: any) => void;
}
interface Names {
}
interface Messages { }

declare function bean(): any;

declare function handler(params: { virtual?: boolean; bindingName: string; validatable?: boolean; }): any;

declare function component(params: { name: string; template: string; }): any;

interface KnockoutStatic {
	ViewModel: {
		new(): {
			$el: HTMLElement;
			readonly $user: {
				readonly contractCode: string;
				readonly companyId: string;
				readonly companyCode: string;
				readonly isEmployee: boolean;
				readonly employeeId: string;
				readonly employeeCode: string;
				readonly selectedLanguage: {
					readonly basicLanguageId: string;
					readonly personNameLanguageId: string;
				};
				readonly role: {
					readonly attendance: string | null;
					readonly companyAdmin: string | null;
					readonly groupCompanyAdmin: string | null;
					readonly officeHelper: string | null;
					readonly payroll: string | null;
					readonly personalInfo: string | null;
					readonly personnel: string | null;
					readonly systemAdmin: string | null;
				};
			};
			readonly $program: {
				webapi: 'com' | 'pr' | 'hr' | 'at';
				programId: string;
				programName: string;
				path: string;
				isDebugMode: boolean;
			};
			readonly $date: {
				now(): Date;
				today(): Date;
			};
			readonly $i18n: {
				(textId: string): string;
				(textId: string, params: string[]): string;
				readonly text: {
					(textId: string): string;
					(textId: string, params: string[]): string;
				};
				readonly message: {
					(messageId: string): string;
					(messageId: string, params: string[]): string;
				};
				controlName(name: string): string;
			};
			readonly $ajax: {
				(url: string): JQueryDeferred<any>;
				(url: string, data: any): JQueryDeferred<any>;
				(webapp: 'com' | 'hr' | 'pr' | 'at', url: string, data: any): JQueryDeferred<any>;
			};
			readonly $window: {
				readonly size: {
					(height: string | number, width: string | number): void;
					readonly width: (width: string) => void;
					readonly height: (height: string) => void;
				};
				readonly close: {
					(): void;
					(result: any): void;
				};
				readonly modal: {
					(url: string): JQueryDeferred<any>;
					(url: string, data: any): JQueryDeferred<any>;
					(webapp: 'com' | 'hr' | 'pr' | 'at', url: string): JQueryDeferred<any>;
					(webapp: 'com' | 'hr' | 'pr' | 'at', url: string, data: any): JQueryDeferred<any>;
				};
				readonly modeless: {
					(url: string): JQueryDeferred<any>;
					(url: string, data: any): JQueryDeferred<any>;
					(webapp: 'com' | 'hr' | 'pr' | 'at', url: string): JQueryDeferred<any>;
					(webapp: 'com' | 'hr' | 'pr' | 'at', url: string, data: any): JQueryDeferred<any>;
				};
				readonly storage: {
					(name: string): JQueryDeferred<any>;
					(name: string, params: any): JQueryDeferred<any>;
				};
			}
			readonly $dialog: {
				readonly info: {
					(options: { messageId: string; }): JQueryDeferred<void>;
					(options: { messageId: string; messageParams: string[]; }): JQueryDeferred<void>;
				};
				readonly alert: {
					(options: { messageId: string; }): JQueryDeferred<void>;
					(options: { messageId: string; messageParams: string[]; }): JQueryDeferred<void>;
				};
				readonly error: {
					(options: { messageId: string; }): JQueryDeferred<void>;
					(options: { messageId: string; messageParams: string[]; }): JQueryDeferred<void>;
				};
				readonly confirm: {
					(options: { messageId: string; }): JQueryDeferred<'no' | 'yes' | 'cancel'>;
					(options: { messageId: string; messageParams: string[]; }): JQueryDeferred<'no' | 'yes' | 'cancel'>;
				};
			}
			readonly $blockui: (act: 'show' | 'hide' | 'clear' | 'invisible' | 'grayout') => JQueryDeferred<void>;
			readonly $validate: {
				(): JQueryDeferred<boolean>;
				(selector: string): JQueryDeferred<boolean>;
				(selectors: string[]): JQueryDeferred<boolean>;
				(...selectors: string[]): JQueryDeferred<boolean>;
				readonly constraint: {
					(): JQueryDeferred<{ [name: string]: any }>;
					(name: string): JQueryDeferred<any>;
					(name: string, value: any): JQueryDeferred<void>;
				};
			};
			readonly $errors: {
				(): JQueryDeferred<boolean>;
				(act: 'clear'): JQueryDeferred<boolean>;
				(act: 'clear', names: string[]): JQueryDeferred<boolean>;
				(act: 'clear', ...names: string[]): JQueryDeferred<boolean>;
				(name: string, messageId: string): JQueryDeferred<boolean>;
				(name: string, message: { messageId: string }): JQueryDeferred<boolean>;
				(name: string, message: { messageId: string; params: string[]; }): JQueryDeferred<boolean>;
				(errors: { [name: string]: { messageId: string; params?: string[]; } }): JQueryDeferred<boolean>;
			}
			readonly $jump: {
				(url: string): void;
				(url: string, params: any): void;
				(webapp: 'com' | 'hr' | 'pr' | 'at', url: string): void;
				(webapp: 'com' | 'hr' | 'pr' | 'at', url: string, params: any): void;
				readonly self: {
					(url: string): void;
					(url: string, params: any): void;
					(webapp: 'com' | 'hr' | 'pr' | 'at', url: string): void;
					(webapp: 'com' | 'hr' | 'pr' | 'at', url: string, params: any): void;
				}
				readonly blank: {
					(url: string): void;
					(url: string, params: any): void;
					(webapp: 'com' | 'hr' | 'pr' | 'at', url: string): void;
					(webapp: 'com' | 'hr' | 'pr' | 'at', url: string, params: any): void;
				}
			};

            /**
             * $nextTick be call when DOM is ready or all descendant component, binding are updated. 
             */
			readonly $nextTick: {
				(cb: () => void): number;
			};
		};
	}
}