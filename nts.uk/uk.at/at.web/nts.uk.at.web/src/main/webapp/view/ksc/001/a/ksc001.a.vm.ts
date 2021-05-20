module nts.uk.at.view.ksc001.a {
    export module viewmodel {
        export class ScreenModel {

            pgName: KnockoutObservable<string> = ko.observable('');

            constructor() {
                const self = this;
                self.getProgramName();
            }
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                 let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            /**
             * request to create creation screen
             */
            createScreen(): void {
                let self = this;
                nts.uk.request.jump("/view/ksc/001/b/index.xhtml");
            }
            /**
             * request to reference history screen
             */
            referenceHistoryScreen(): void {
                let self = this;
               nts.uk.request.jump("/view/ksc/001/g/index.xhtml");
            }

            getProgramName() {
                const self = this;
                const namePath = nts.uk.text.format('sys/portal/standardmenu/findProgramName/{0}/{1}', 'KSC001', 'A');
                nts.uk.request.ajax('com', namePath).then((value: string) => {
                    if (!_.isNil(value)) {
                        self.pgName(value);
                    }
                });
            }
        }
    }
}