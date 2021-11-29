/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp002.a {
  import getText = nts.uk.resource.getText;
  import getShared = nts.uk.ui.windows.getShared;
  import blockUI = nts.uk.ui.block;
  import modal = nts.uk.ui.windows.sub.modal;
  import setShared = nts.uk.ui.windows.setShared;

  // URL API backend
  const API = {
    GET_INITIAL_STARTUP: "at/record/workrecord/stampmanagement/support/initialStartupSupportCard",
    REGISTER_SUPPORT_CARD: "at/record/workrecord/stampmanagement/support/registerSupportCard",
    RENEWAL_SUPPORT_CARD: "at/record/workrecord/stampmanagement/support/renewalSupportCard",
    DELETE_SUPPORT_CARD: "at/record/workrecord/stampmanagement/support/deleteSupportCard",
    GET_REGISTRATION_INFO_CARD: "at/record/workrecord/stampmanagement/support/getRegistrationInfoSupportCard"
  };

  @bean()
  export class ViewModel extends ko.ViewModel {
    columns = ko.observableArray([
      {headerText: getText("KMP002_6"), key: 'supportCardNumber', width: 120},
      {headerText: getText("KMP002_7"), key: 'companyCode', width: 100},
      {headerText: getText("KMP002_8"), key: 'workplaceCode', width: 250}
    ]);
    supportCardList: KnockoutObservableArray<SupportCardDto> = ko.observableArray([]);
    supportCard: KnockoutObservable<SupportCardDto> = ko.observable(null);
    currentCard: KnockoutObservable<any> = ko.observable(null);
    date: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
    loginCompanyInfo: KnockoutObservable<CompanyInfo> = ko.observable(null);
    NEW_MODE = 2;
    NORMAL_MODE = 1;
    mode: KnockoutObservable<number> = ko.observable(this.NEW_MODE);

    created() {
      const self = this;
      // get all support card
      self.performInitialStartup(true, true);
      // selection support card
      self.currentCard.subscribe((supportCardNumber: any) => {
        if (!supportCardNumber) {
          return;
        }
        self.mode(self.NORMAL_MODE);
        self.supportCard(self.getSupportCardById(supportCardNumber));
        if (self.supportCard().companyId === self.loginCompanyInfo().companyId) {
          self.applyBaseDate();
        }
      });
    }

    performInitialStartup(isInitSupportCard: boolean = false, isSetMode: boolean = false, indexSupportCard: number = 0) {
      const vm = this;
      vm.$ajax(API.GET_INITIAL_STARTUP)
        .then((data: any) => {
          if (data) {
            vm.initData(new InitialStartup(data.supportCards, data.companyInfos, data.workplaceInfors, null), isInitSupportCard, isSetMode, indexSupportCard);
          }
        });
    }

    initData(data: InitialStartup, isInitSupportCard: boolean, isSetMode: boolean, indexSupportCard: number) {
      const vm = this;
      vm.supportCardList.removeAll();
      const loginCompany = vm.getCompanyInfo(data.companyInfos, vm.$user.companyId);
      vm.loginCompanyInfo(loginCompany);
      data.supportCards.forEach((item: SupportCard) => {
        const workplace = vm.getWorkplaceInfo(data.workplaceInfors, item.workplaceId);
        const company = vm.getCompanyInfo(data.companyInfos, item.companyId);
        const supportCard = vm.$user.companyId === item.companyId ?
          new SupportCardDto(item.supportCardNumber + '', item.supportCardNumber, item.companyId, company.companyCode, company.companyName, item.workplaceId, workplace.workplaceCode, workplace.workplaceName) :
          new SupportCardDto(item.supportCardNumber + '', item.supportCardNumber, item.companyId, company.companyCode, '', item.workplaceId, workplace.workplaceCode, '');
        vm.supportCardList.push(supportCard);
      });
      if (isInitSupportCard) {
        vm.initSupportCard(indexSupportCard);
      }
      if (isSetMode) {
        data.supportCards.length === 0 ? vm.mode(vm.NEW_MODE) : vm.mode(vm.NORMAL_MODE);
      }
    }

    initSupportCard(indexSupportCard: number) {
      const vm = this;
      if (vm.supportCardList().length === 0) {
        vm.supportCard(new SupportCardDto('', 0, vm.loginCompanyInfo().companyId, vm.loginCompanyInfo().companyCode, vm.loginCompanyInfo().companyName, '', '', ''));
      } else {
        indexSupportCard = indexSupportCard >= vm.supportCardList().length ? indexSupportCard - 1 : indexSupportCard;
        vm.supportCard(vm.supportCardList()[indexSupportCard]);
        vm.currentCard(vm.supportCard().supportCardNumber);
      }
    }

    getWorkplaceInfo(workplaceInfos: WorkplaceInfor[], workplaceId: string): WorkplaceInfor {
      let workplaceInfo = null;
      workplaceInfos.forEach((item) => {
        if (item.workplaceId === workplaceId) {
          workplaceInfo = item;
          return;
        }
      });
      return workplaceInfo;
    }

    getCompanyInfo(companyInfos: CompanyInfo[], companyId: string): CompanyInfo {
      let companyInfo = null;
      companyInfos.forEach((item) => {
        if (item.companyId === companyId) {
          companyInfo = item;
          return;
        }
      });
      return companyInfo;
    }

    getSupportCardById(supportCardNumber: any): SupportCardDto {
      const vm = this;
      let supportCard = null;
      vm.supportCardList().forEach((item) => {
        if (item.supportCardNumber == supportCardNumber) {
          supportCard = item;
          return;
        }
      });
      return supportCard;
    }

    showFormInput() {
      const vm = this;
      vm.mode(vm.NEW_MODE);
      vm.currentCard(null);
      vm.supportCard(new SupportCardDto('', 0, vm.loginCompanyInfo().companyId, vm.loginCompanyInfo().companyCode, vm.loginCompanyInfo().companyName, '', '', ''));
    }

    register() {
      const vm = this;
      if (vm.supportCard().workplaceId === '') {
        vm.$dialog.error({ messageId: "Msg_2130" });
        return;
      }
      if (vm.mode() === vm.NEW_MODE) {
        if (vm.supportCard().supportCardNo() === '') {
          return;
        }
        vm.supportCard().supportCardNumber = parseInt(vm.supportCard().supportCardNo());
        vm.$ajax(API.REGISTER_SUPPORT_CARD, vm.supportCard())
          .then((data: any) => {
            vm.$dialog.info({ messageId: "Msg_15" });
            vm.performInitialStartup(true, true);
          }).fail((err) => {
            vm.$dialog.error({ messageId: err.messageId });
          });
        return;
      } else {
        // Register for renewal of support card
        const indexUpdate = vm.getIndexOfSupportCard();
        vm.$ajax(API.RENEWAL_SUPPORT_CARD, vm.supportCard())
          .then((data: any) => {
            vm.$dialog.info({ messageId: "Msg_15" });
            vm.performInitialStartup(true, false, indexUpdate);
          });
        return;
      }
    }

    getIndexOfSupportCard() {
      const vm = this;
      let indexUpdate = 0;
      vm.supportCardList().forEach((item: SupportCardDto, index: number) => {
        if (item.supportCardNumber === vm.supportCard().supportCardNumber) {
          indexUpdate = index;
          return;
        }
      });
      return indexUpdate;
    }

    deleteCard() {
      const vm = this;
      vm.$dialog.confirm({ messageId: 'Msg_18' })
        .then((result: 'no' | 'yes' | 'cancel') => {
          // when the deletion process is continued
          if (result === 'yes') {
            const indexDelete = vm.getIndexOfSupportCard();
            // delete the support card
            vm.$ajax(API.DELETE_SUPPORT_CARD, vm.supportCard())
              .then((data: any) => {
                vm.$dialog.info({ messageId: "Msg_16" });
                vm.performInitialStartup(true, true, indexDelete);
              });
          }
        });
    }

    openDialogKMP002b() {
      modal('/view/kmp/002/b/index.xhtml');
    }

    applyBaseDate() {
      // get the registration information of the selected card NO
      const vm = this;
      const param = {
        companyId: vm.supportCard().companyId,
        workplaceId: vm.supportCard().workplaceId,
        baseDate: moment(vm.date()).format('YYYY/MM/DD')
      }
      vm.$ajax(API.GET_REGISTRATION_INFO_CARD, param)
        .then((data: any) => {
          if (data) {
            const supportCardNo = vm.supportCard().supportCardNo();
            const supportCardNumber = vm.supportCard().supportCardNumber;
            const companyId = vm.supportCard().companyId;
            const companyCode = vm.supportCard().companyCode;
            const companyName = data.companyName;
            const workplaceId = vm.supportCard().workplaceId;
            const workplaceCode = vm.supportCard().workplaceCode;
            const workplaceName = workplaceId ? data.workplaceName : '';
            vm.supportCard(new SupportCardDto(supportCardNo, supportCardNumber, companyId, companyCode, companyName, workplaceId, workplaceCode, workplaceName));
          }
        });
    }

    openDialogCDL008() {
      const vm = this;
      blockUI.invisible();
      setShared('inputCDL008', {
        baseDate: moment(vm.date()).toDate(),
        isMultiple: false,
        selectedSystemType: 2,
        isrestrictionOfReferenceRange: false,
        showNoSelection: false,
        isShowBaseDate: false,
        startMode: 0
      });
      modal('com', '/view/cdl/008/a/index.xhtml').onClosed(() => {
        const workplaceInfor = getShared('workplaceInfor')[0];
        const supportCardNo = vm.supportCard().supportCardNo();
        const supportCardNumber = vm.supportCard().supportCardNumber;
        const companyId = vm.supportCard().companyId;
        const companyCode = vm.supportCard().companyCode;
        const companyName = vm.supportCard().companyName;
        const workplaceId = workplaceInfor.id;
        const workplaceCode = workplaceInfor.code;
        const workplaceName = workplaceInfor.name;
        vm.supportCard(new SupportCardDto(supportCardNo, supportCardNumber, companyId, companyCode, companyName, workplaceId, workplaceCode, workplaceName));
        blockUI.clear();
      });
    }

    disableNewBtn(): boolean {
      const vm = this;
      return vm.mode() === vm.NORMAL_MODE ? false : true;
    }

    disableRegisterBtn(): boolean {
      const vm = this;
      if (vm.mode() === vm.NEW_MODE)
        return false;
      return vm.supportCard().companyId === vm.loginCompanyInfo().companyId ? false : true;
    }

    disableDeleteBtn(): boolean {
      const vm = this;
      if (vm.mode() === vm.NEW_MODE)
        return true;
      return vm.supportCard().companyId === vm.loginCompanyInfo().companyId ? false : true;
    }

    disableDate(): boolean {
      const vm = this;
      if (vm.mode() === vm.NEW_MODE)
        return false;
      return vm.supportCard().companyId === vm.loginCompanyInfo().companyId ? false : true;
    }

	}

  class InitialStartup {
    supportCards: SupportCard[];
    companyInfos: CompanyInfo[];
    workplaceInfors: WorkplaceInfor[];
    supportCardEdit: any;

    constructor(supportCards: [], companyInfos: [], workplaceInfors: [], supportCardEdit: any) {
      this.supportCards = supportCards;
      this.companyInfos = companyInfos;
      this.workplaceInfors = workplaceInfors;
      this.supportCardEdit = supportCardEdit;
    }
  }

  class SupportCard {
    supportCardNumber: number;
    companyId: string;
    workplaceId: string;

    constructor(supportCardNumber: number, companyId: string, workplaceId: string) {
      this.supportCardNumber = supportCardNumber;
      this.companyId = companyId;
      this.workplaceId = workplaceId;
    }
  }

  class CompanyInfo {
    companyCode: string;
    companyName: string;
    companyId: string;

    constructor(companyCode: string, companyName: string, companyId: string) {
      this.companyCode = companyCode;
      this.companyName = companyName;
      this.companyId = companyId;
    }
  }

  class WorkplaceInfor {
    workplaceId: string;
    workplaceCode: string;
    workplaceName: string;

    constructor(workplaceId: string, workplaceCode: string, workplaceName: string) {
      this.workplaceId = workplaceId;
      this.workplaceCode = workplaceCode;
      this.workplaceName = workplaceName;
    }
  }

  class SupportCardDto {
    supportCardNo: KnockoutObservable<string>;
    supportCardNumber: number;
    companyId: string;
    companyCode: string;
    companyName: string;
    workplaceId: string;
    workplaceCode: string;
    workplaceName: string;

    constructor(supportCardNo: string, supportCardNumber: number, companyId: string, companyCode: string,
      companyName: string, workplaceId: string, workplaceCode: string, workplaceName: string) {
      this.supportCardNo = ko.observable(supportCardNo);
      this.supportCardNumber = supportCardNumber;
      this.companyId = companyId;
      this.companyCode = companyCode;
      this.companyName = companyName;
      this.workplaceId = workplaceId;
      this.workplaceCode = workplaceCode;
      this.workplaceName = workplaceName;
    }
  }

}
