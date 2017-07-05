module nts.uk.at.view.kmk005.i {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel {
        export class ScreenModel {
            model: KnockoutObservable<BonusPaySetting> = ko.observable(new BonusPaySetting({ id: '', name: '' }));
            constructor() {
                let self = this;

                self.start();
            }

            start() {
                let self = this,
                    model = self.model();

                service.getData().done(resp => {
                    if (resp) {
                        model.id(resp.bonusPaySettingCode);
                        service.getName(resp.bonusPaySettingCode).done(x => model.name(x.name)).fail(x => alert(x));
                    } else {
                        model.id('000');
                        model.name(getText("KDL007_6"));
                    }
                }).fail(x => alert(x));
            }

            openBonusPaySettingDialog() {
                let self = this,
                    model: BonusPaySetting = self.model();

                setShared("KDL007_PARAM", { isMulti: false, posibles: [], selecteds: [model.id()] });

                modal('../../../kdl/007/a/index.xhtml').onClosed(() => {
                    let data: any = getShared('KDL007_VALUES');
                    if (data && data.selecteds) {
                        let code: string = data.selecteds[0];
                        if (code) {
                            model.id(code);
                            service.getName(code).done(resp => {
                                if (resp) {
                                    model.name(resp.name);
                                }
                                else {
                                    model.id('000');
                                    model.name(getText("KDL007_6"));
                                }
                            }).fail(x => alert(x));
                        } else {
                            model.id('000');
                            model.name(getText("KDL007_6"));
                        }
                    }
                });
            }

            saveData() {

            }

            removeData() {

            }
        }
    }


    interface IBonusPaySetting {
        id: string;
        name: string;
    }

    class BonusPaySetting {
        id: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');

        constructor(param: IBonusPaySetting) {
            let self = this;

            self.id(param.id);
            self.name(param.name);
        }
    }
}