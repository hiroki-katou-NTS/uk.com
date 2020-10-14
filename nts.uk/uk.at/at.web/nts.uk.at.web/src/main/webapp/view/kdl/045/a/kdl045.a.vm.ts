module nts.uk.at.view.kdl045.a {

    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;

    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<any>;
            selectedTab: KnockoutObservable<string>;
            
            timeRange: any;
            
            dataSourceTime: KnockoutObservableArray<any>;
            
            timeRange1Value: KnockoutObservable<any>;
            timeRange2Value: KnockoutObservable<any>;
            
            constructor() {
                let self = this;
                self.tabs = ko.observableArray([
                   { id: 'tab-1', title: getText('KDL045_6'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                   { id: 'tab-2', title: getText('KDL045_7'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
               ]);
                self.selectedTab = ko.observable('tab-1');
                
                //listData
                self.dataSourceTime = ko.observableArray([{ range1: ko.observable({ startTime: 10, endTime: 100 })}]);
                self.timeRange = {
                    maxRow: 10,
                    minRow: 0,
                    maxRowDisplay: 10,
                    isShowButton: true,
                    dataSource: self.dataSourceTime,
                    isMultipleSelect: true,
                    columns: self.settingColumns(),
                    tabindex: 94
                };
                
                self.timeRange1Value = ko.observable({ startTime: 10, endTime: 100 });
                self.timeRange2Value = ko.observable({ startTime: 101, endTime: 150 });
            }               

            startPage(): JQueryPromise<any> {
                let self = this;

//                block.invisible();
                let dfd = $.Deferred();
//                let data: IDataTransfer = getShared('kdl044Data');
//                if (data == null)
//                    return;
//                self.dataTransfer(data);
//                self.isMultiSelect(data.isMultiSelect);
//
//                let paras: any;
//                switch (data.filter) {
//                    case 0: {
//                        paras = { targetUnit: null, workplaceIds: null, workplaceGroupId: null };
//                        break;
//                    }
//					// lấy bằng workplaceID
//                    case 1: {
//                        paras = { targetUnit: 0, workplaceId: data.filterIDs[0], workplaceGroupId: null };
//                        break;
//                    }
//					// lấy bằng workplaceGroupId
//                    case 2: {
//                        paras = { targetUnit: 1, workplaceId: null, workplaceGroupId: data.filterIDs[0] };
//                        break;
//                    }
//                }
//                service.getShiftMaster(paras).done(function (result) {
//                    service.isMultipleManagement()
//                        .done((isUse) => {
//                            let isUseWorkMultiple = isUse && isUse.workMultiple == 1;
//                            self.createHeader(isUseWorkMultiple);
//                            //UI処理[1]
//                            if (data.permission) {
//                                if (isUseWorkMultiple) {
//                                    result.push(new Shifuto(
//                                        "  ",
//                                        getText('KDL044_13'),
//                                        "",
//                                        "",
//                                        "",
//                                        "",
//                                        ""
//                                    ));
//                                } else {
//                                    result.push(new Shifuto(
//                                        "  ",
//                                        getText('KDL044_13'),
//                                        "",
//                                        "",
//                                        "",
//                                        ""
//                                    ));
//                                }   
//                            }
//                            self.listShifuto();
//                            let differentFromCurrents = null;
//                            if (data.isMultiSelect == true) {
//								differentFromCurrents = _.differenceWith(result, data.shiftCodeExpel, (a, b) => { return a.shiftMasterCode === b });
//							} else {
//								differentFromCurrents = _.filter(result, (val) => { return val.shiftMasterCode != data.shiftCodeExpel });
//                            }
//                            
//                            self.listShifuto(_.sortBy(differentFromCurrents, 'shiftMasterCode'));
//                            if (data.shifutoCodes != null) {
//                                self.selectedCodes(data.shifutoCodes);
//                            }
//                            dfd.resolve();
//                        });
//
//                }).fail(function (res: any) {
//                    alertError({ messageId: "" });
//                    block.clear();
//                });
//                block.clear();
                dfd.resolve();
                return dfd.promise();
            }


            closeDialog() {
                nts.uk.ui.windows.close();
            }
            
            
            
            private settingColumns(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KDL045_30"), key: "range1", defaultValue: ko.observable({ startTime: 0, endTime: 0 }),
                    width: 243, template: 
                    `<div data-bind="ntsTimeRangeEditor: {required: true,
                            inputFormat: 'time',
                            startTimeNameId: '#[KDL045_32]',
                            endTimeNameId: '#[KDL045_33]',
                            startConstraint: 'TimeWithDayAttr',
                            endConstraint: 'TimeWithDayAttr',
                            paramId: 'KDL045_31'
                                }
                            "/>`
                }
            ];
            }
        }

        class Shifuto {
            shiftMasterCode: string;
            shiftMasterName: string;
            workTypeName: string;
            workTimeName: string;
            workTime1: string;
            workTime2: string;
            remark: string;
            constructor(code: string,
                name: string,
                workType: string,
                workTime: string,
                time1: string,
                time2: string,
                remark: string) {
                let self = this;
                self.shiftMasterCode = code;
                self.shiftMasterName = name;
                self.workTypeName = workType;
                self.workTimeName = workTime;
                self.workTime1 = time1 ? time1 : "";
                self.workTime2 = time2 ? time2 : "";
                self.remark = remark ? remark : "";
            }
        }

        //Data from other screens
        export interface IDataTransfer {

            /**
             * 選択モード                               
             *  複数選択: true, 単一選択 : false   
             */
            isMultiSelect: boolean,

            /**
             * 未選択許可区分
             * True：選択肢[なし]を表示する
             * False：選択肢[なし]を表示しない"
             */
            permission: boolean,

            /**
             * 絞り込みしない:0/職場:1/職場グループ:2
             * ※[絞り込みしない] →　全件表示
             */
            filter: number,

            /**
             * List<絞り込み対象ID>                               
             * 上記絞り込みを行う対象の職場/職場グループ    
            */
            filterIDs?: Array<string>,

            /**
             * List<選択済みシフトマスタコード>
             * 画面起動時に選択状態とするシフトマスタ      
             */
            shifutoCodes?: Array<string>

			/**
			 * 0,1 : WorkPlace
			 * 2 : WorkPlaceGroup		
			 */
			workPlaceType?: number;

			shiftCodeExpel?: Array<string>
        }
    }
}