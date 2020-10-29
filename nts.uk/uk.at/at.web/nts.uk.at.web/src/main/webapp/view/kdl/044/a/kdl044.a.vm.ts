module nts.uk.at.view.kdl044.a {

    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;

    export module viewmodel {
        export class ScreenModel {
            columns: KnockoutObservableArray<any>;
            listShifuto: KnockoutObservableArray<Shifuto> = ko.observableArray([]);
            //listShifuto: Array<IShifuto> = [];
            selectedCodes: KnockoutObservableArray<String> = ko.observableArray([]);
            isMultiSelect: KnockoutObservable<boolean> = ko.observable(true);
            placeHolders: string = "";
            dataTransfer: KnockoutObservable<any> = ko.observable();
            gridFields: Array<String>;
            constructor() {
                let self = this;
                let holders = getText('KDL044_2') + '・' + getText('KDL044_3') + '・' + getText('KDL044_6') + '・'
                    + getText('KDL044_7') + '・' + getText('KDL044_8') +  'で検索…';
                self.placeHolders = holders;
            }

            startPage(): JQueryPromise<any> {
                let self = this;

                block.invisible();
                let dfd = $.Deferred();
                let data: IDataTransfer = getShared('kdl044Data');
                if (data == null)
                    return;
                self.dataTransfer(data);
                self.isMultiSelect(data.isMultiSelect);

                let paras: any;
                switch (data.filter) {
                    case 0: {
                        paras = { targetUnit: null, workplaceIds: null, workplaceGroupId: null };
                        break;
                    }
					// lấy bằng workplaceID
                    case 1: {
                        paras = { targetUnit: 0, workplaceId: data.filterIDs[0], workplaceGroupId: null };
                        break;
                    }
					// lấy bằng workplaceGroupId
                    case 2: {
                        paras = { targetUnit: 1, workplaceId: null, workplaceGroupId: data.filterIDs[0] };
                        break;
                    }
                }
                service.getShiftMaster(paras).done(function (result) {
                    service.isMultipleManagement()
                        .done((isUse) => {
                            let isUseWorkMultiple = isUse && isUse.workMultiple == 1;
                            self.createHeader(isUseWorkMultiple);
                            //UI処理[1]
                            if (data.permission) {
                                if (isUseWorkMultiple) {
                                    result.push(new Shifuto(
                                        "  ",
                                        getText('KDL044_13'),
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""
                                    ));
                                } else {
                                    result.push(new Shifuto(
                                        "  ",
                                        getText('KDL044_13'),
                                        "",
                                        "",
                                        "",
                                        ""
                                    ));
                                }   
                            }
                            self.listShifuto();
                            let differentFromCurrents = null;
                            if (data.isMultiSelect == true) {
								differentFromCurrents = _.differenceWith(result, data.shiftCodeExpel, (a, b) => { return a.shiftMasterCode === b });
							} else {
								differentFromCurrents = _.filter(result, (val) => { return val.shiftMasterCode != data.shiftCodeExpel });
                            }
                            
                            self.listShifuto(_.sortBy(differentFromCurrents, 'shiftMasterCode'));
                            if (data.shifutoCodes != null) {
                                self.selectedCodes(data.shifutoCodes);
                            }
                            dfd.resolve();
                        });

                }).fail(function (res: any) {
                    alertError({ messageId: "" });
                    block.clear();
                });
                block.clear();
                return dfd.promise();
            }

            createHeader(isUse: boolean) {
                let self = this;
                if (!isUse) {
                    self.columns = ko.observableArray([
                        { headerText: getText('KDL044_2'), key: "shiftMasterCode", dataType: "string", width: 50, formatter: _.escape},
                        { headerText: getText('KDL044_3'), key: "shiftMasterName", dataType: "string", width: 70 , formatter: _.escape},
/*                        { headerText: getText('KDL044_4'), key: "workTypeName", dataType: "string", width: 100 },
                        { headerText: getText('KDL044_5'), key: "workTimeName", dataType: "string", width: 100 },*/
                        { headerText: getText('KDL044_6'), key: "workTime1", dataType: "string", width: 300 , formatter: _.escape},
                        { headerText: getText('KDL044_8'), key: "remark", dataType: "string", width: 300 , formatter: _.escape}
                    ]);
                    self.gridFields = ["shiftMasterCode", "shiftMasterName", "workTypeName", "workTimeName", "workTime1", "remark"];
                } else {
                    self.columns = ko.observableArray([
                        { headerText: getText('KDL044_2'), key: "shiftMasterCode", dataType: "string", width: 50 , formatter: _.escape},
                        { headerText: getText('KDL044_3'), key: "shiftMasterName", dataType: "string", width: 70 , formatter: _.escape},
/*                        { headerText: getText('KDL044_4'), key: "workTypeName", dataType: "string", width: 100 },
                        { headerText: getText('KDL044_5'), key: "workTimeName", dataType: "string", width: 100 },*/
                        { headerText: getText('KDL044_6'), key: "workTime1", dataType: "string", width: 200 , formatter: _.escape},
                        { headerText: getText('KDL044_7'), key: "workTime2", dataType: "string", width: 200 , formatter: _.escape},
                        { headerText: getText('KDL044_8'), key: "remark", dataType: "string", width: 200 , formatter: _.escape}
                    ]);
                    self.gridFields = ["shiftMasterCode", "shiftMasterName", "workTypeName", "workTimeName", "workTime1", "workTime2", "remark"];
                }
            }

            decide() {
                let self = this;
                block.invisible();
                /**
                 * UI処理[2]
                 * 選択状態チェック
                 * 画面パラメータ[未選択許可区分]＝False and A2_1[シフト選択]の選択状態:  未選択    →   エラー
                 */
                if (!self.dataTransfer().permission && self.selectedCodes().length == 0 && self.isMultiSelect() == false) {
                    alertError({ messageId: "Msg_1629" });
                    block.clear();
                    return;
                }

                setShared('kdl044ShifutoCodes', self.selectedCodes());
                let kdl044ShifutoData = _.filter(self.listShifuto(), (val) => { return self.selectedCodes().indexOf(val.shiftMasterCode) !== -1 });
                setShared('kdl044ShifutoData', kdl044ShifutoData);
                setShared('kdl044_IsCancel', false);
                block.clear();
                nts.uk.ui.windows.close();
            }

            closeDialog() {
                setShared('kdl044_IsCancel', true);
                nts.uk.ui.windows.close();
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