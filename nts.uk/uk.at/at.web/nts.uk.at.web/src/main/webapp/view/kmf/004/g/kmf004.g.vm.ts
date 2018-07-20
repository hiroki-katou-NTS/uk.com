module nts.uk.at.view.kmf004.g.viewmodel {

    import getText = nts.uk.resource.getText;
    import alError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog.info;
    import formatDate = nts.uk.time.formatDate;
    import parseYearMonthDate = nts.uk.time.parseYearMonthDate;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        // list relationship A2_2
        lstRelationship: KnockoutObservableArray<Relationship> = ko.observableArray([]);;
        // column in list
        gridListColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KMF004_5"), key: 'relationshipCode', width: 80 },
            { headerText: nts.uk.resource.getText("KMF004_6"), key: 'relationshipName', width: 150, formatter: _.escape },
            { headerText: nts.uk.resource.getText("KMF004_8"), key: 'setting', width: 80, formatter: makeIcon }]);
        // selected code 
        selectedCode: KnockoutObservable<string> = ko.observable("");;
        constructor() {
            let self = this;

            self.selectedCode.subscribe((value) => {

            });

        }


        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            service.findAll().done((data: Array<any>) => {
                self.lstRelationship(_.map(data, item => { return new Relationship(item) }));
                self.selectedCode(_.size(data) ? data[0].relationshipCode : "");
                dfd.resolve();
            }).fail((error) => { alError({ messageId: error.messageId, messageParams: error.parameterIds }); })
                .always(() => {
                    block.clear();
                    dfd.resolve();
                });
            return dfd.promise();
        }

        /** update or insert data when click button register **/
        register() {
            let self = this;

        }


        /** remove item from list **/
        remove() {
            let self = this;
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                nts.uk.ui.dialog.info({ messageId: "Msg_16" });
            }).ifNo(() => {
            });
        }

        close() {
            nts.uk.ui.windows.close();
        }

    }
    export class Relationship {
        relationshipCode: string = "";
        relationshipName: string = "";
        threeParentOrLess: boolean = false;
        constructor(data?) {
            if (data) {
                this.relationshipCode = data.relationshipCode;
                this.relationshipName = data.relationshipName;
                this.threeParentOrLess = data.threeParentOrLess == 1 ? true : false;
            }
        }
    }
}
function makeIcon(value, row) {
    if (value == "true")
        return "<i data-bind='ntsIcon: { no: 78 }'></i>";
    return '';
}




