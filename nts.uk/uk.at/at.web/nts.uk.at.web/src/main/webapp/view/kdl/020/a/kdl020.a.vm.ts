module nts.uk.at.view.kdl020.a.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import block = nts.uk.ui.block;
    import jump = nts.uk.request.jump;
    import alError = nts.uk.ui.dialog.alertError;

    export class ViewModel {

        columns = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);
        data = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);
        value: KnockoutObservable<string> = ko.observable('');
        constructor() {
            let self = this;

        }
        start(): JQueryPromise<any> {            block.invisible();
            var self = this,
                dfd = $.Deferred();
            block.clear();

            dfd.resolve();

            return dfd.promise();
        
    } }
}