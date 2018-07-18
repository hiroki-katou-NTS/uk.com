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
            {
                headerText: nts.uk.resource.getText("KMF004_8"), key: 'setting', width: 80,
                template: '{{if ${setting} == "true"}} <i data-bind=\'ntsIcon: { no: 78 }\'></i>{{else }}  {{/if}}'
            }]);
        // selected code 
        selectedCode: KnockoutObservable<string> = ko.observable("");
        selectedName: KnockoutObservable<string> = ko.observable("");
        selectedSHENo: KnockoutObservable<number> = ko.observable(null);
        currentGrantDay: KnockoutObservable<GrantDayRelationship> = ko.observable(new GrantDayRelationship());
        constructor() {
            let self = this;
            block.invisible();
            self.selectedCode.subscribe((value) => {
                if (value != null) {
                    service.findByCode(self.selectedSHENo(), value).done((data) => {
                        self.selectedName(_.find(self.lstRelationship(), { 'relationshipCode': value }).relationshipName);
                        self.currentGrantDay(new GrantDayRelationship(data));
                        self.currentGrantDay().relationshipCd(value);
                        self.currentGrantDay().specialHolidayEventNo(self.selectedSHENo());

                    })
                        .fail((error) => { alError({ messageId: error.messageId, messageParams: error.parameterIds }); })
                        .always(() => {
                            block.clear();
                        });
                }
            });

        }


        /** get data when start dialog **/
        startPage(isReload?): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            if (!isReload) {
                let sHENo = getShared("SHeNo");
                self.selectedSHENo(sHENo);
            }
            block.invisible();
            service.findAll(self.selectedSHENo()).done((data: Array<any>) => {

                self.lstRelationship(_.map(data, item => { return new Relationship(item) }));
                if (isReload) {
                    self.selectedCode.valueHasMutated();
                    _.each($('td i'), icon => ko.bindingHandlers.ntsIcon.init(icon, () => ({ no: 78 })));
                } else {
                    self.selectedCode(_.size(data) ? data[0].relationshipCode : "");
                }
            }).fail((error) => { alError({ messageId: error.messageId, messageParams: error.parameterIds }); })
                .always(() => {
                    block.clear();
                    dfd.resolve();
                });
            return dfd.promise();
        }

        /** update or insert data when click button register **/
        register() {
            let self = this,
                cmd = ko.toJS(self.currentGrantDay());

            $(".date_input").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            block.invisible();
            service.update(cmd).done(() => {
                dialog({ messageId: "Msg_15" }).then(() => {
                    self.startPage(true);
                });
            }).fail((error) => { alError({ messageId: error.messageId, messageParams: error.parameterIds }); })
                .always(() => {
                    block.clear();
                });


        }


        /** remove item from list **/
        remove() {
            let self = this;
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let cmd = ko.toJS(self.currentGrantDay());
                block.invisible();
                service.remove(cmd.specialHolidayEventNo, cmd.relationshipCd).done(() => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                        self.startPage(true);
                    });;
                }).fail((error) => { alError({ messageId: error.messageId, messageParams: error.parameterIds }); })
                    .always(() => {
                        block.clear();
                    });
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
        setting: boolean = false;
        constructor(data?) {
            if (data) {
                this.relationshipCode = data.relationshipCode;
                this.relationshipName = data.relationshipName;
                this.threeParentOrLess = data.threeParentOrLess == 1 ? true : false;
                this.setting = data.setting;
            }
        }
    }
    export class GrantDayRelationship {

        relationshipCd: KnockoutObservable<string> = ko.observable("");
        grantedDay: KnockoutObservable<number> = ko.observable(null);
        morningHour: KnockoutObservable<number> = ko.observable(null);
        createNew: KnockoutObservable<boolean> = ko.observable(true);
        specialHolidayEventNo: KnockoutObservable<number> = ko.observable(null);
        constructor(data?) {
            if (data) {
                this.relationshipCd(data.relationshipCd);
                this.grantedDay(data.grantedDay);
                this.morningHour(data.morningHour);
                this.createNew(false);
            }
        }

    }

}




