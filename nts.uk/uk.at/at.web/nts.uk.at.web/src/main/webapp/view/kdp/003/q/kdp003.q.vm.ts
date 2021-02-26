/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.q {
    import MessageNotice = nts.uk.at.kdp003.p.MessageNotice;
    import Role = nts.uk.at.kdp003.p.Role;

    const API = {

        //社員が作成するお知らせ登録の画面を表示する
        INIT_SCREEN: 'sys/portal/notice/notificationCreatedByEmp',

        //お知らせを登録する
        REGISTER_NOTICE: 'sys/portal/notice/registerMessageNotice',

        //お知らせを更新する
        UPDATE_NOTICE: 'sys/portal/notice/updateMessageNotice',

        //お知らせを削除する
        DELETE_NOTICE: 'sys/portal/notice/deleteMessageNotice',

        //打刻入力の作成するお知らせ登録の画面を表示する
        DISPLAY_NOTICE: 'at/record/stamp/notice/displayNoticeRegisterScreen',

        //打刻入力のお知らせの職場を取得する
        GET_WKP_BY_STAMP_NOTICE: 'at/record/stamp/notice/getWkpByStampNotice'
    };

    const COMMA = '、';

    @bean()
	export class ViewModel extends ko.ViewModel {
        messageText: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable('');
		endDate: KnockoutObservable<string> = ko.observable('');
		updateDate: KnockoutObservable<string> = ko.observable('更新日:');

        destination: KnockoutObservable<number> = ko.observable(1);
        dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
            startDate: moment.utc().format('YYYY/MM/DD'),
			endDate: moment.utc().format('YYYY/MM/DD')
             }));
        isNewMode: KnockoutObservable<boolean> = ko.observable(false);
        isActiveDelete: KnockoutComputed<boolean> = ko.computed(() => !this.isNewMode());
        assignAtr: KnockoutObservable<number> = ko.observable(0);
        employeeReferenceRange: KnockoutObservable<number> = ko.observable(0);

        parentParam = new ParentParam();
        isStartUpdateMode = false;
        workPlaceIdList: KnockoutObservableArray<string> = ko.observableArray([]);
        workPlaceName: KnockoutObservableArray<string> = ko.observableArray(['職場1', '職場2', '職場3', '職場4']);
        workPlaceText: KnockoutComputed<string> = ko.computed(() => {
            return this.workPlaceName().join(COMMA);
          });

        created(parentParam: ParentParam) {
            const vm = this;
            vm.parentParam = parentParam;
        }

        mounted(){
            const vm = this;
			$(document).ready(() => {
				$('#message').focus();
			});
        }

        /**
         * 起動する
         */
        onStartScreen(){
            const vm = this;
            const param = {};
            vm.$ajax(API.INIT_SCREEN).then(() => {

            });
        }

        /**
         * Q20_1：登録をクリックする
         */
        onClickRegister(){
            const vm = this;
            const error: string = vm.checkBeforeRegister();
            if (!_.isEmpty(error)) {
              vm.$dialog.error({ messageId: error });
              return;
            }
            if (vm.isNewMode()) {
              vm.registerOnNewMode();
            } else {
              vm.registerOnUpdateMode();
            }
          }

        /**
         * Q1 登録前のチェックについて
         */
        checkBeforeRegister(): string {
            return '';
        }

        /**
         * ※新規モードの場合
         */
        registerOnNewMode() {

        }

        /**
         * ※更新モードの場合
         */
        registerOnUpdateMode() {

        }

        /**
         * Q20_2: 削除をクリックする
         */
        onClickDelete() {

        }

        /**
         * Q20_3：職場選択をクリックする
         */
        openKDP003KDialog() {

        }

        /**
         * Q20_4: 戻る
         */
        returnP() {
            const vm = this;
			vm.$window.close();
        }

    }
    enum DestinationClassification {
        ALL = 0,
        WORKPLACE = 1,
        DEPARTMENT = 2
      }
    
    enum StartMode {
        WORKPLACE = 0,
        DEPARTMENT = 1
    }
    
    enum EmployeeReferenceRange {
        ALL_EMPLOYEE = 0,
        DEPARTMENT_AND_CHILD = 1,
        DEPARTMENT_ONLY = 2,
        ONLY_MYSELF = 3
      }

    export interface WorkplaceInfo {
        workplaceId: string; //職場ID
        workplaceCode: string; //職場コード
        workplaceName: string; //職場表示名
    }

    export class ParentParam {
        isNewMode: boolean;
        role: Role;
        messageNotice: MessageNotice
      }

    export class DatePeriod {
        startDate: string;
        endDate: string;
    
        constructor(init?: Partial<DatePeriod>) {
          $.extend(this, init);
        }
      }

    export class NotificationParams {
        refeRange: number;
        msg: MessageNotice;
        constructor(init?: Partial<NotificationParams>) {
          $.extend(this, init);
        }
      }

    export class WorkplaceInfo {
        workplaceId: string;
        workplaceCode: string;
        workplaceName: string;
      }
    
    export interface NotificationCreated {
        workplaceInfo: WorkplaceInfo;
        targetWkps: WorkplaceInfo[];
        //targetEmps: EmployeeInfo[];
      }
    
}