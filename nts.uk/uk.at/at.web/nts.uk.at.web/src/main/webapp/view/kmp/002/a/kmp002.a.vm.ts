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
      {headerText: getText("KMP002_6"), key: 'supportCardId', width: 120},
      {headerText: getText("KMP002_7"), key: 'companyCode', width: 100},
      {headerText: getText("KMP002_8"), key: 'workplaceCode', width: 250}
    ]);
    supportCardList: KnockoutObservableArray<SupportCardDto> = ko.observableArray([]);
    supportCard: KnockoutObservable<SupportCardDto> = ko.observable(null);
    currentCard: KnockoutObservable<any> = ko.observable(null);
    supportCardEdit: SupportCardEdit = null;
    date: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
    loginCompanyInfo: KnockoutObservable<CompanyInfo> = ko.observable(null);
    NEW_MODE = 2;
    NORMAL_MODE = 1;
    mode: KnockoutObservable<number> = ko.observable(this.NEW_MODE);
    isFocusInput: KnockoutObservable<boolean> = ko.observable(false);
    isFocusInputText: KnockoutObservable<boolean> = ko.observable(false);

    created() {
      const self = this;
      // get all support card
      self.performInitialStartup(true, true);
      // selection support card
      self.currentCard.subscribe((supportCardId: any) => {
        if (!supportCardId) {
          return;
        }
        self.mode(self.NORMAL_MODE);
        self.supportCard(self.getSupportCardById(supportCardId));
        if (self.supportCard() && self.supportCard().companyId === self.loginCompanyInfo().companyId) {
          self.applyBaseDate();
        }
      });
      self.mode.subscribe(() => {
        if (self.mode() === self.NEW_MODE) {
          self.isFocusInput(true);
        } else {
          self.isFocusInput(false);
        }
      });
      self.isFocusInput.subscribe(() => {
        if (self.supportCard().supportCardNo() === '') {
          return;
        }
        if (self.isFocusInput()) {
          self.supportCard().supportCardNo(self.supportCard().supportCardNumber + '');
        } else {
          const supportCardNumber = parseInt(self.supportCard().supportCardNo());
          self.supportCard().supportCardNumber = supportCardNumber;
          const supportCardNo = self.editSupportCardNumber(supportCardNumber);
          const companyId = self.supportCard().companyId;
          const companyCode = self.supportCard().companyCode;
          const companyName = self.supportCard().companyName;
          const workplaceId = self.supportCard().workplaceId;
          const workplaceCode = self.supportCard().workplaceCode;
          const workplaceName = self.supportCard().workplaceName;
          self.supportCard(new SupportCardDto(supportCardNo, '', supportCardNumber, companyId, companyCode, companyName, workplaceId, workplaceCode, workplaceName));
        }
      });
      self.isFocusInputText.subscribe(() => {
        if (self.isFocusInputText()) {
          self.isFocusInput(true);
        }
      })
    }

    performInitialStartup(isInitSupportCard: boolean = false, isSetMode: boolean = false, indexSupportCard: number = 0) {
      const vm = this;
      vm.$ajax(API.GET_INITIAL_STARTUP)
        .then((data: any) => {
          if (data) {
            vm.initData(new InitialStartup(data.supportCards, data.companyInfos, data.workplaceInfors, data.supportCardEdit), isInitSupportCard, isSetMode, indexSupportCard);
          }
        });
    }

    initData(data: InitialStartup, isInitSupportCard: boolean, isSetMode: boolean, indexSupportCard: number) {
      const vm = this;
      vm.supportCardList.removeAll();
      const loginCompany = vm.getCompanyInfo(data.companyInfos, vm.$user.companyId);
      vm.loginCompanyInfo(loginCompany);
      vm.supportCardEdit = data.supportCardEdit;
      data.supportCards.forEach((item: SupportCard) => {
        const workplace = vm.getWorkplaceInfo(data.workplaceInfors, item.workplaceId);
        const company = vm.getCompanyInfo(data.companyInfos, item.companyId);
        const supportCard = vm.$user.companyId === item.companyId ?
          new SupportCardDto(vm.editSupportCardNumber(item.supportCardNumber),
            vm.editSupportCardNumber(item.supportCardNumber),
            item.supportCardNumber,
            item.companyId, company.companyCode,
            company.companyName, item.workplaceId,
            workplace.workplaceCode,
            workplace.workplaceName) :
          new SupportCardDto(vm.editSupportCardNumber(item.supportCardNumber),
            vm.editSupportCardNumber(item.supportCardNumber),
            item.supportCardNumber,
            item.companyId,
            company.companyCode,
            '',
            item.workplaceId,
            workplace.workplaceCode,
            '');
        vm.supportCardList.push(supportCard);
      });
      vm.supportCardList(_.orderBy(vm.supportCardList(), ['supportCardNumber'], ['asc']));
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
        vm.supportCard(new SupportCardDto('', '', 0, vm.loginCompanyInfo().companyId, vm.loginCompanyInfo().companyCode, vm.loginCompanyInfo().companyName, '', '', ''));
      } else {
        indexSupportCard = indexSupportCard >= vm.supportCardList().length ? indexSupportCard - 1 : indexSupportCard;
        vm.supportCard(vm.supportCardList()[indexSupportCard]);
        vm.currentCard(vm.supportCard().supportCardId);
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

    getSupportCardById(supportCardId: any): SupportCardDto {
      const vm = this;
      let supportCard = null;
      vm.supportCardList().forEach((item) => {
        if (item.supportCardId == supportCardId) {
          supportCard = item;
          return;
        }
      });
      return supportCard;
    }

    editSupportCardNumber(supportCardNumber: number): string {
      const vm = this;
      let supportCardNo = supportCardNumber + '';
      if (!vm.supportCardEdit) {
        return supportCardNo;
      }
      if (vm.supportCardEdit.editMethod === SupportCardEditSettingEnum.PreviousZero) {
        return vm.appendSettingToData('0', false, supportCardNo);
      } else if (vm.supportCardEdit.editMethod === SupportCardEditSettingEnum.AfterZero) {
        return vm.appendSettingToData('0', true, supportCardNo);
      } else if (vm.supportCardEdit.editMethod === SupportCardEditSettingEnum.PreviousSpace) {
        return vm.appendSettingToData(' ', false, supportCardNo);
      } else if (vm.supportCardEdit.editMethod === SupportCardEditSettingEnum.AfterSpace) {
        return vm.appendSettingToData(' ', true, supportCardNo);
      }
      return supportCardNo;
    }

    appendSettingToData(editMethod: string, isAfter: boolean, data: string): string {
      while(data.length < 6) {
        if (isAfter) {
          data = data + editMethod;
        } else {
          data = editMethod + data;
        }
      }
      return data;
    }

    showFormInput() {
      const vm = this;
      vm.mode(vm.NEW_MODE);
      vm.currentCard(null);
      vm.supportCard(new SupportCardDto('', '', 0, vm.loginCompanyInfo().companyId, vm.loginCompanyInfo().companyCode, vm.loginCompanyInfo().companyName, '', '', ''));
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
      const vm = this;
      modal('/view/kmp/002/b/index.xhtml').onClosed(() => {
        const dataOutput = getShared('KMP002B_Output');
        if (dataOutput !== undefined && dataOutput !== null && vm.supportCardEdit.editMethod !== dataOutput) {
          vm.supportCardEdit = new SupportCardEdit(dataOutput);
          const supportCardLst = vm.supportCardList();
          vm.resetSupportCardList(supportCardLst);
        }
      });
    }

    resetSupportCardList(supportCardLst: SupportCardDto[]) {
      const vm = this;
      supportCardLst.forEach((item, index) => {
        const data = new SupportCardDto(vm.editSupportCardNumber(item.supportCardNumber),
          vm.editSupportCardNumber(item.supportCardNumber),
          item.supportCardNumber,
          item.companyId, item.companyCode,
          item.companyName, item.workplaceId,
          item.workplaceCode,
          item.workplaceName);
        vm.supportCardList.splice(index, 1, data);
      });
      const data = new SupportCardDto(vm.editSupportCardNumber(vm.supportCard().supportCardNumber),
        vm.editSupportCardNumber(vm.supportCard().supportCardNumber),
        vm.supportCard().supportCardNumber,
        vm.supportCard().companyId, vm.supportCard().companyCode,
        vm.supportCard().companyName, vm.supportCard().workplaceId,
        vm.supportCard().workplaceCode,
        vm.supportCard().workplaceName);
      vm.supportCard(data);
      vm.currentCard(vm.supportCard().supportCardId);
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
            vm.supportCard(new SupportCardDto(supportCardNo, supportCardNo, supportCardNumber, companyId, companyCode, companyName, workplaceId, workplaceCode, workplaceName));
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
        vm.supportCard(new SupportCardDto(supportCardNo, supportCardNo, supportCardNumber, companyId, companyCode, companyName, workplaceId, workplaceCode, workplaceName));
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
      if (!vm.supportCard()) {
        return;
      }
      return vm.supportCard().companyId === vm.loginCompanyInfo().companyId ? false : true;
    }

    disableDeleteBtn(): boolean {
      const vm = this;
      if (vm.mode() === vm.NEW_MODE)
        return true;
      if (!vm.supportCard()) {
        return;
      }
      return vm.supportCard().companyId === vm.loginCompanyInfo().companyId ? false : true;
    }

    disableDate(): boolean {
      const vm = this;
      if (vm.mode() === vm.NEW_MODE)
        return false;
      if (!vm.supportCard()) {
        return;
      }
      return vm.supportCard().companyId === vm.loginCompanyInfo().companyId ? false : true;
    }

	}

  class InitialStartup {
    supportCards: SupportCard[];
    companyInfos: CompanyInfo[];
    workplaceInfors: WorkplaceInfor[];
    supportCardEdit: SupportCardEdit;

    constructor(supportCards: [], companyInfos: [], workplaceInfors: [], supportCardEdit: SupportCardEdit) {
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

  class SupportCardEdit {
    editMethod: number;

    constructor(editMethod: number) {
      this.editMethod = editMethod;
    }
  }

  class SupportCardDto {
    supportCardNo: KnockoutObservable<string>;
    supportCardId: string;
    supportCardNumber: number;
    companyId: string;
    companyCode: string;
    companyName: string;
    workplaceId: string;
    workplaceCode: string;
    workplaceName: string;

    constructor(supportCardNo: string, supportCardId: string, supportCardNumber: number, companyId: string, companyCode: string,
      companyName: string, workplaceId: string, workplaceCode: string, workplaceName: string) {
      this.supportCardNo = ko.observable(supportCardNo);
      this.supportCardId = supportCardId;
      this.supportCardNumber = supportCardNumber;
      this.companyId = companyId;
      this.companyCode = companyCode;
      this.companyName = companyName;
      this.workplaceId = workplaceId;
      this.workplaceCode = workplaceCode;
      this.workplaceName = workplaceName;
    }
  }

  export enum SupportCardEditSettingEnum {
    // 前ゼロ
    PreviousZero = 0,

    // 後ろゼロ
    AfterZero = 1,

    // 前スペース
    PreviousSpace = 2,

    // 後ろスペース
    AfterSpace = 3
  }

}
