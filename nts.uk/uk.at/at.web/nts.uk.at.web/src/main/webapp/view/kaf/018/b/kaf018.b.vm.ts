 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.b.viewmodel {
	import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import formatDate = nts.uk.time.formatDate;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.alertError;
    import shareModel = kaf018.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import block = nts.uk.ui.block;

    @bean()
    class Kaf018BViewModel extends ko.ViewModel {
		tempData: Array<model.ConfStatusDta> = [];
        enable: KnockoutObservable<boolean> = ko.observable(true);
        listWorkplaceId: KnockoutObservableArray<string> = ko.observableArray([]);
        closureId: number = 0;
        closureName: string = '対象締め';
        processingYm: string = '対象締め';
        startDate: Date = null;
        endDate: Date = null;
        isDailyComfirm: boolean = false;
        listEmployeeCode: Array<any> = [];
        listWorkplace: Array<model.WorkplaceInfor> = [];
        inputContent: any = null;
        isCheckedAll: KnockoutObservable<boolean> = ko.observable(false);

		// refactor 5
		maxItemDisplay: KnockoutObservable<number> = ko.observable(100);
		
        created(params: any) {
			const self = this;
			window.onresize = function(event: any) {
            	$("#gridB_scrollContainer").height(window.innerHeight - 269);
            	$("#gridB_displayContainer").height(window.innerHeight - 269);
            	$("#gridB_container").height(window.innerHeight - 240);
            };
			self.closureName = 'closureName';
			// self.tempData.push(new model.ConfStatusDta('', '', false, false, '', '', '', ''));
			// self.initNtsGrid([]);
			self.createMGrid();
			let wkpInfoLst = params.selectWorkplaceInfo,
				wsParam = { wkpInfoLst };
			self.$ajax('at', API.getStatusExecution, wkpInfoLst);
//			self.startPage().done(() => {
//				$("#fixed-table").focus();
//			});
        }
		
		createMGrid() {
			const self = this;
			let buttonHtml = `<button style="margin-top: 10px" data-bind="click: buttonMailAction, text: $i18n('KAF018_346')"></button>`;
			new nts.uk.ui.mgrid.MGrid(document.getElementById('dpGrid'), {
//                width: '570px',
//                height: '300px',
//                subWidth: "1000px",
                subHeight: '300px',
                headerHeight: '70px',
                dataSource: [
					new model.TableItem('wkp1', 1, 1),
					new model.TableItem('wkp2', 2, 2),
					new model.TableItem('wkp3', 3, 3)
				],
                primaryKey: 'wkpName',
                primaryKeyDataType: 'string',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                hidePrimaryKey: true,
                columns: [
					{ headerText: self.$i18n('KAF018_331'), key: 'wkpName', dataType: 'string', width: '500px' },
					{ headerText: self.$i18n('KAF018_332'), key: 'numberPeople', dataType: 'string', width: '50px' },
					{ headerText: self.$i18n('KAF018_333')+buttonHtml, key: 'appInfo', width: '85px'
//						group: [
//							{ headerText: buttonHtml, key: 'mailButton', width: '84px' }	
//						]
					}	
				],
                ntsControls: [],
                features: [
					{
		                name: 'HeaderStyles',
		                columns: [
		                    { key: 'wkpName', colors: ['kaf018-b-header-wkpName'] },
							{ key: 'numberPeople', colors: ['kaf018-b-header-numberPeople'] },
							{ key: 'appInfo', colors: ['kaf018-b-header-appInfo'] },
							{ key: 'mailButton', colors: ['kaf018-b-header-mailButton'] }
		                ]
		            },
				]
            }).create();
		}
		
		buttonMailAction() {
			alert('go to mail');	
		}
		
		createMgridHeader() {
			const self = this;
			let result: Array<any> = [];
			
			return result;
		}
		
		startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
//            let params: model.IParam = nts.uk.ui.windows.getShared('KAF018BInput');
//            self.closureId = params.closureId;
//            self.closureName = params.closureName;
//            self.processingYm = params.processingYm;
//            self.startDate = params.startDate;
//            self.endDate = params.endDate;
//            self.listEmployeeCode = params.listEmployeeCode;
//            self.isDailyComfirm = params.isConfirmData;
//            self.listWorkplace = params.listWorkplace;
//            self.inputContent = params.inputContent;
            
            let obj = {
                startDate: self.startDate,
                endDate: self.endDate,
                isConfirmData: self.isDailyComfirm,
                listWorkplace: self.listWorkplace,
                listEmpCd: self.listEmployeeCode
            };
            self.$ajax('at', API.getAppSttByWorkpace, obj).done(function(data: any) {
                _.forEach(data, function(item) {
                    self.tempData.push(new model.ConfStatusDta(item.workplaceId, item.workplaceName,item.enabled, item.checked, 
                            self.getRecord(item.approvedNumOfCase ? item.approvedNumOfCase : 0),
                            self.getRecord1(item.numOfApp ? item.numOfApp : 0, item.numOfUnreflected ? item.numOfUnreflected : 0),
                            self.getRecord(item.numOfUnapproval ? item.numOfUnapproval : 0),
                            self.getRecord(item.numOfDenials ? item.numOfDenials : 0)));
                })
                self.initNtsGrid(self.listHidden());
                dfd.resolve();
                block.clear();
            }).fail(function() {
                block.clear();
            })
            return dfd.promise();
        }

        sendMails() {
            var self = this;
            block.invisible();
            let confirmStatus: Array<model.UnApprovalSendMail> = [];
            _.forEach(self.tempData, function(item) {
                if (item.isChecked) {
                    confirmStatus.push(new model.UnApprovalSendMail(item.workplaceId, item.isChecked));
                }
            });

            self.$ajax('at', API.getCheckSendMail, confirmStatus).done(function() {
                confirm({ messageId: "Msg_795" }).ifYes(() => {
                    block.invisible();
                    let listWkpId = [];
                    _.forEach(confirmStatus, function(item) {
                        listWkpId.push(item.workplaceId);
                    });
                    let obj = {
                        listWkpId: listWkpId,
                        closureStart: self.startDate,
                        closureEnd: self.endDate,
                        listEmpCd: self.listEmployeeCode
                    };
                    self.$ajax('at', API.exeSendUnconfirmedMail, obj).done(function(result: any) {
                        shareModel.showMsgSendEmail(result);
                    }).fail(function(err) {
                        error({ messageId: err.messageId });
                    }).always(function() {
                        block.clear();
                    });
                })
            }).fail(function(err) {
                error({ messageId: err.messageId });
            }).always(function() {
                block.clear();
            });
        }

        getRecord1(value1: number, value2: number): string {
            let val2: string =  value2 > 0 ? value2 : "";
            let val1: string = value1 > 0 ? value1 + "件" : "";
            let symb = (val1 != "" && val2 != "") ? "/" : "";
            return val2 + symb + val1;
        }

        getRecord(value?: number) {
            return value ? value + "件" : "";
        }

        getTargetDate(): string {
            var self = this;
            let startDate = nts.uk.time.formatDate(new Date(self.startDate), 'yyyy/MM/dd');
            let endDate = nts.uk.time.formatDate(new Date(self.endDate), 'yyyy/MM/dd');
            return self.processingYm + " (" + startDate + " ～ " + endDate + ")";
        }

        goBackA() {
            var self = this;
            let params = {
                inputContent: self.inputContent
            };
             nts.uk.request.jump('/view/kaf/018/a/index.xhtml', params);    
        }

        gotoC(id) {
            var self = this;
            let listWorkplace = [];
            let indexs = null;
            _.each(self.tempData, function(item, index) {
                listWorkplace.push(new shareModel.ItemModel(item.workplaceId, item.workplaceName));
                if(item.workplaceId == id){
                    indexs = index;
                }
            });
            let params = {
                closureId: self.closureId,
                closureName: self.closureName,
                processingYm: self.processingYm,
                startDate: self.startDate,
                endDate: self.endDate,
                listWorkplace: listWorkplace,
                selectedWplIndex: indexs,
                listEmployeeCode: self.listEmployeeCode,
                inputContent: self.inputContent
            };
            nts.uk.request.jump('/view/kaf/018/c/index.xhtml', params);
        }
        listHidden(): Array<any>{
            let self = this;
            let lstHidden = [];
             _.each(self.tempData, function(item, index) {
                if(item.isEnabled == false){
                    lstHidden.push(item.workplaceId);
                }
            });
            return lstHidden;
        }
        initNtsGrid(lstHidden: Array<any>) {
            var self = this;
            $("#gridB").ntsGrid({
                width: '850px',
                height: window.innerHeight - 240 + 'px',
                dataSource: self.tempData,
                primaryKey: 'workplaceId',
                rowVirtualization: true,
                virtualization: true,
                hidePrimaryKey: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: getText('KAF018_20'), key: 'workplaceName', dataType: 'string', width: '310px', ntsControl: 'LinkLabel' },
                    { headerText: getText('KAF018_21'), key: 'numOfUnreflected', dataType: 'string', width: '100px' },
                    { headerText: getText('KAF018_22'), key: 'numOfUnapproval', dataType: 'string', width: '100px'},
                    { headerText: getText('KAF018_23'), key: 'approvedNumOfCase', dataType: 'string', width: '100px'},
                    { headerText: getText('KAF018_24'), key: 'numOfDenials', dataType: 'string', width: '100px'},
                    { headerText: getText('KAF018_25'), key: 'isChecked', dataType: 'boolean', width: '120px', 
                            showHeaderCheckbox: true, ntsControl: 'Checkbox',  hiddenRows: lstHidden},
                    { headerText: 'ID', key: 'workplaceId', dataType: 'string', width: '0px', ntsControl: 'Label'}
                ],
                features: [
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }
                ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox' },
                    { name: 'LinkLabel' ,click: function(rowId){self.gotoC(rowId)}, controlType: 'LinkLabel' }],
            });
            $("#gridB").setupSearchScroll("igGrid", true);
            $("#gridB").focus();
        }
    }

	export module model {

        export class UnApprovalSendMail {
            workplaceId: string;
            isChecked: boolean;
            constructor(workplaceId: string, isChecked: boolean) {
                this.workplaceId = workplaceId;
                this.isChecked = isChecked;
            }
        }

        export class IParam {
            closureId: number;

            /** The closure name. */
            closureName: string;

            /** The start date. */
            startDate: Date;

            /** The end date. */
            endDate: Date;

            /** The closure date. */
            //処理年月
            closureDate: number;

            processingYm: string;

            isConfirmData: boolean;

            listEmployeeCode: Array<any>;

            listWorkplace: Array<WorkplaceInfor>;
        }

        export class WorkplaceInfor {
            // 職場ID
            code: string;

            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        export class ConfStatusDta {
            workplaceId: string;
            workplaceName: string;
            isEnabled: boolean;
            isChecked: boolean;
            /**承認済件数*/
            approvedNumOfCase: string;
            /**未反映件数*/
            numOfUnreflected: string;
            /**未承認件数*/
            numOfUnapproval: string;
            /**否認件数*/
            numOfDenials: string;
            constructor(workplaceId: string, workplaceName: string,isEnabled: boolean, isChecked: boolean,
                approvedNumOfCase: any, numOfUnreflected: any, numOfUnapproval: any, numOfDenials: any) {
                this.workplaceId = workplaceId;
                this.workplaceName = workplaceName;
                this.isEnabled = isEnabled;
                this.approvedNumOfCase = approvedNumOfCase;
                this.numOfUnreflected = numOfUnreflected;
                this.numOfUnapproval = numOfUnapproval;
                this.numOfDenials = numOfDenials;
                this.isChecked = isChecked;
            }
        }

		// refactor 5
		export class TableItem {
			wkpName: string;	
			numberPeople: number;
			appInfo: number;
			constructor(wkpName: string, numberPeople: number, appInfo: number) {
				this.wkpName = wkpName;
				this.numberPeople = numberPeople;
				this.appInfo = appInfo;
			}
		}
    }

    const API = {
		getAppSttByWorkpace: "at/request/application/approvalstatus/getAppSttByWorkpace",
        exeSendUnconfirmedMail: "at/request/application/approvalstatus/exeSendUnconfirmedMail",
        getCheckSendMail: "at/request/application/approvalstatus/getCheckSendMail",
		// refactor 5
		getStatusExecution: "at/request/application/approvalstatus/getStatusExecution"
    }
}