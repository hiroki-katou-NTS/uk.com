module nts.uk.com.view.cps001.g.vm {
        import getText = nts.uk.resource.getText;
        export class ScreenModel {

            // Store create/update mode
            createMode: KnockoutObservable<boolean>;
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            date: KnockoutObservable<string>;   
            roundingRules: KnockoutObservableArray<any>;
            value: KnockoutObservable<number>;
            selectedRuleCode: any;
            constructor() {
                let _self = this;
                _self.date = ko.observable('20000101');
                _self.value= ko.observable(1200);
                _self.createMode = ko.observable(null);
                
                _self.roundingRules = ko.observableArray([
                    { code: '1', name: '四捨五入' },
                    { code: '2', name: '切り上げ' }
                ]);
                _self.selectedRuleCode = ko.observable(1);
                _self.createMode.subscribe((newValue) => {
                });
                this.columns = ko.observableArray([
                    { headerText:  '', dataType: 'string',width: 30 },
                    { headerText:  getText('CPS001_110'), dataType: 'string', prop: 'code', width: 50 },
                    { headerText: getText('CPS001_111'), dataType: 'string', prop: 'name', width: 50 },
                    { headerText: getText('CPS001_120'), dataType: 'string', prop: 'description', width: 50 },
                    { headerText: getText('CPS001_128'), dataType: 'string', prop: 'other1', width: 50 },
                    { headerText: getText('CPS001_121'), dataType: 'string', prop: 'other2', width: 50 },
                    { headerText: getText('CPS001_122'), dataType: 'string', prop: 'other2', width: 50 },
                    { headerText: getText('CPS001_123'), dataType: 'string', prop: 'other2', width: 50 },
                    { headerText: getText('CPS001_124'), dataType: 'string', prop: 'other2', width: 50 },
                     { headerText: getText('CPS001_129'), dataType: 'string', prop: 'other2', width: 50 }
                ]);

                this.items = ko.observableArray([]);
                let str = ['a0', 'b0', 'c0', 'd0'];
                for (let j = 0; j < 4; j++) {
                    for (let i = 1; i < 51; i++) {
                        let code = i < 10 ? str[j] + '0' + i : str[j] + i;
                        this.items.push(new ItemModel(code, code, code, code));
                    }
                }
                this.currentCode = ko.observable();
            }



            // BEGIN PAGE BEHAVIOUR

            /**
             * Run after page loaded
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                // Load sequence data list
                return dfd.promise();
            }

            /**
             * Start create mode
             */
            public startCreateMode(): void {
                let _self = this;
                _self.createMode(true);
            }

            /**
             * Save sequence
             */
            public save(): void {
                let _self = this;

                if (_self.createMode()) {
                } else {
                }
            }

            /**
             * Close this dialog
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }

            /**
             * Remove sequence
             */
            public remove(): void {
                let _self = this;

                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                    .ifYes(() => {

                    }).ifNo(() => {
                        // Nothing happen
                    })
            }
        }
        
        class ItemModel {
            code: string;
            name: string;
            description: string;
            other1: string;
            other2: string;
            constructor(code: string, name: string, description: string, other1?: string, other2?: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other1 = other1;
                this.other2 = other2 || other1;
            }
        }

}