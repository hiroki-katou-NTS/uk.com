module nts.uk.ui.at.kdp013.d {

    export type DataContent = {
        id: string;
        targetDate: string;
        description: string;
        link: string;
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        dataSource: KnockoutObservableArray<DataContent> = ko.observableArray([]);

        constructor(params: DataContent[]) {
            super();

            if (!params || !params.length) {
                this.close();
            }

            // binding to grid
            this.dataSource(params);
        }

        mounted() {
            const vm = this;

            $(vm.$el)
                // patch click link
                // note: change pseudo if DataContent change property
                .on('click', 'td[aria-describedby="kdw-013-ddata_link"]', (evt: JQueryEventObject) => {
                    const ds = ko.unwrap(vm.dataSource);
                    const di = $(evt.target).closest('tr[data-id]').data('id');
                    const exist = _.find(ds, ({ id }) => id === di);

                    if (exist) {
                        console.log(exist);

                        // logic per record at here
                        // note: change jump data
                        if (exist.id === '02') {
                            vm.$jump('at', '/view/kaf/005/a/index.xhtml', exist);
                        } else {
                            vm.$jump('at', '/view/kaf/006/a/index.xhtml', exist);
                        }
                    }
                });
        }

        // close dialog
        close() {
            const vm = this;

            vm.$window.close();
        }
    }
}