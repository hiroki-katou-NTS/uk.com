module nts.uk.at.view.kdw006.b {
    export module viewmodel {
        export class ScreenModel {
            //Daily perform id from other screen
            dailyPerformId = '';

            //Define textbox
            textAreaValue: KnockoutObservable<string> = ko.observable("123");
            multilineeditor: any = {
                value: this.textAreaValue,
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "",
                    width: "380px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };

            //Define switch button
            selectedRuleCode: any = ko.observable(1);

            constructor() {
                var self = this;
                self.textAreaValue('afafad');
            }

            update() {
                var self = this;
                var perform = {
                    settingUnit: self.selectedRuleCode(),
                    comment: self.textAreaValue()
                };

                //Day len server tblUser
                service.update(perform).done(function(data) {
                    self.getDailyPerform();
                });
            };

            getDailyPerform() {
                let self = this;
                var dfd = $.Deferred();
                service.getDailyPerform().done(function(data) {
                    
                    let perform = new DailyPerform(data);

                    self.selectedRuleCode(perform.settingUnit);
                    self.textAreaValue(perform.comment);

                    dfd.resolve();
                });
                return dfd.promise();

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                
                dfd.resolve();
                
//                self.getDailyPerform().done(function() {
//                    dfd.resolve();
//                });
                
                return dfd.promise();
            }

        }

        export class DailyPerform {
            settingUnit: number;
            comment: string;
            constructor(x:any) {
                let self = this;
                if (x) {
                    self.settingUnit = x.settingUnit;
                    self.comment = x.comment;
                } else {
                    self.settingUnit = 1;
                    self.comment = '';
                }
            }
        }
    }
}
