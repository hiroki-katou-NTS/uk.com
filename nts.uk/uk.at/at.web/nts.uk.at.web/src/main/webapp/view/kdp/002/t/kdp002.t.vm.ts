module nts.uk.at.view.kdp002.t {
    export module viewmodel {
        export class ScreenModel {
            dataShare: KnockoutObservableArray<any> = ko.observableArray([]);
            labelNames: KnockoutObservable<string> = ko.observable('');
            labelColor : KnockoutObservable<string> = ko.observable('');
            buttonInfo: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
            constructor() {
            }
            /**
             * start page  
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                //self.dataShare = nts.uk.ui.windows.getShared('KDP010_2');
                let lstButton: ItemModel[] = [];
                for (let i = 1; i < 6; i++) {
                    lstButton.push(new ItemModel('', '', ''));
                }
                self.buttonInfo(lstButton);
                self.dataShare = {
                    messageContent : '個人の打刻入力で利用する設定です',
                    messageColor: '#0033cc',
                    listRequired : [{
                        buttonName : ko.observable('個人の打'),
                        buttonColor : ko.observable('#ccccff'),
                        textColor : ko.observable('#0033cc')
                    }]    
                }
                dfd.resolve();
                return dfd.promise();
            }
            getData(newValue: number): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                return dfd.promise();
            }
            
            public getInfoButton(lstButtonSet: any, buttonLayoutType: number) {
                let self = this, buttonLength = 0;
                if (lstButtonSet == null) {
                    for (let i = 0; i < 6; i++) {
                        self.buttonInfo()[i].buttonColor("#999");
                        self.buttonInfo()[i].buttonName(null);
                        self.buttonInfo()[i].textColor("#999");
                    }
                } else {
                    for (let i = 0; i < 6; i++) {
                        self.buttonInfo()[i].buttonName(lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0] == null ? "" : lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0].buttonDisSet.buttonNameSet.buttonName);
                        if (lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0] == null) {
                            self.buttonInfo()[i].buttonColor("#999");
                            self.buttonInfo()[i].buttonName(null);
                            self.buttonInfo()[i].textColor("#999");
                        } else {
                            self.buttonInfo()[i].buttonColor((lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0].buttonDisSet.backGroundColor));
                            self.buttonInfo()[i].buttonName(lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0].buttonDisSet.buttonNameSet.buttonName);
                            self.buttonInfo()[i].textColor((lstButtonSet.filter(x => x.buttonPositionNo == i + 1)[0].buttonDisSet.buttonNameSet.textColor));
                        }
                    }
                }

            }


            /**
             * Close dialog
             */
            public closeDialog(): void {
                let shareG = [];
                nts.uk.ui.windows.setShared('KDP010_T', shareG);
                nts.uk.ui.windows.close();
            }

        }

    }
     export class ItemModel {
            buttonName: string;
            buttonColor: string;
            textColor: string;

            constructor(buttonName: string, buttonColor: string, textColor: string) {
                this.buttonName = ko.observable('') || '';
                this.buttonColor = ko.observable('') || '';
                this.textColor = ko.observable('') || '';
            }
        }
}