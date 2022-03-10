module nts.uk.at.view.ksp001.e.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    import block = nts.uk.ui.block;

    export class ScreenModel {

        items: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        mode: number = 0;
        heightGrid: number = 85;

        constructor() {
            let self = this;
            self.mode = getShared('dataD').typeAtr;
            // set tạm headerText, dung jquery sua headerText ben start.js
            self.columns = ko.observableArray([
                { headerText: 'detailType', key: 'detailType', width: 0, hidden: true },
                { headerText: 'name', key: 'name', width: 200 }
            ]);
            if (self.mode == 0) {
                // 通知 
                self.items = ko.observableArray([
                    { detailType: 0, name: getText('KSP001_36') },
                    //                    { detailType: 1, name: getText('KSP001_37') },
                    //                    { detailType: 2, name: getText('KSP001_38') },
                    { detailType: 3, name: getText('KSP001_39') }
                ]);
            } else {
                // 勤怠状況
                self.heightGrid = 240;
                self.items = ko.observableArray([
                    { detailType: 1, name: getText('KSP001_40') },
                    //                    { detailType: 2, name: getText('KSP001_41') },
                    //                    { detailType: 3, name: getText('KSP001_42') },
                    { detailType: 4, name: getText('KSP001_43') },
                    { detailType: 5, name: getText('KSP001_44') },
                    { detailType: 6, name: getText('KSP001_45') },
                    { detailType: 7, name: getText('KSP001_46') },
                    //                    { detailType: 8, name: getText('KSP001_47') },
                    //                    { detailType: 9, name: getText('KSP001_49') },
                    //                    { detailType: 10, name: getText('KSP001_50') },
                    //                    { detailType: 11, name: getText('KSP001_51') }
                ]);
            }

            self.getTopPageDetail();
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * get data from table SPTMT_SP_TOPPAGE_DETAIL
         */
        getTopPageDetail(): JQueryPromise<any> {
            block.grayout();
            let self = this, dfd = $.Deferred();

            service.getTopPageDetail(self.mode).done((data) => {
                let arrChecked: any = [];
                _.each(data, dt => {
                    if (dt.displayAtr == 1) {
                        arrChecked.push(dt.detailType);
                    }
                })
                self.currentCodeList(arrChecked);
                dfd.resolve();
            }).fail(err => {
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
        /**
         * save data
         */
        saveData(): JQueryPromise<any> {
            block.grayout();
            let self = this, dfd = $.Deferred(), params: any = [];
            _.forEach(self.items(), item => {
                let detailType: number = item.detailType;
                let displayAtr: number = _.includes(self.currentCodeList(), detailType.toString()) ? 1 : 0;
                params.push({
                    type: self.mode,
                    detailType: detailType,
                    displayAtr: displayAtr
                });
            });
            service.saveDataDetail({ listSPTopPageDetailDto: params }).done(() => {
                info({ messageId: "Msg_15" }).then(function() {
                    self.closeDialog();
                });
                dfd.resolve();
            }).fail(err => {
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
    }
}