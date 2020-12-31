/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.cdl010.a.screenModel {

  const API = {
    getContactInfomation: 'query/refercontactinfor/get'
  };

  @bean()
  export class ViewModel extends ko.ViewModel {

    detailData: KnockoutObservable<ContactInformationRefer> = ko.observable(new ContactInformationRefer());
    otherContact: KnockoutObservableArray<OtherContact> = ko.observableArray([]);

    created(param: ObjectParam) {
      const vm = this;
      vm.$ajax('com', API.getContactInfomation, param.employeeId)
      // Call API to get ContactInformationRefer
      const detailData: ContactInformationRefer = ({
        businessName: 'ハタケ　カカシ',
        categoryName: '分類１',
        contactInformation: new ContactInformation({
          companyMobilePhoneNumber: '080-1234-5678',
          personalMobilePhoneNumber: '090-1234-xxx8',
          emergencyNumber1: '080-xxx1-5678',
          emergencyNumber2: '080-1234-5678',
          seatDialIn: '123',
          seatExtensionNumber: '3010',
          companyEmailAddress: 'kakashi_hatake@nittsusysutem.co.jp',
          companyMobileEmailAddress: 'hatake_kakashi@docomo.ne.jp',
          personalEmailAddress: 'hatake_kakashi@gmail.com',
          personalMobileEmailAddress: 'hatake_kakashi@docomo.ne.jp',
          otherContactsInfomation: [
            new OtherContact({ contactName: 'Skype', contactAddress: 'hatake_kakashi' }),
            new OtherContact({ contactName: 'Zoom', contactAddress: 'htk_kakashi' }),
            new OtherContact({ contactName: 'Twitter', contactAddress: 'kakashi_twitter' }),
            new OtherContact({ contactName: 'Line', contactAddress: 'kakashi_line' }),
            new OtherContact({ contactName: 'Zalo', contactAddress: 'kakashi_zalo' }),
          ]
        }),
        employmentName: '正社員',
        positionName: '一般職位',
        workplaceName: '第一開発部'
      });
      if (detailData.contactInformation) {
        const companyEmailAddress = detailData.contactInformation.companyEmailAddress;
        const companyMobileEmailAddress = detailData.contactInformation.companyMobileEmailAddress;
        const personalEmailAddress = detailData.contactInformation.personalEmailAddress;
        const personalMobileEmailAddress = detailData.contactInformation.personalMobileEmailAddress;
        detailData.contactInformation.companyEmailAddress = `<a href="mailto:${companyEmailAddress}">${companyEmailAddress}</a>`;
        detailData.contactInformation.companyMobileEmailAddress = `<a href="mailto:${companyMobileEmailAddress}">${companyMobileEmailAddress}</a>`;
        detailData.contactInformation.personalEmailAddress = `<a href="mailto:${personalEmailAddress}">${personalEmailAddress}</a>`;
        detailData.contactInformation.personalMobileEmailAddress = `<a href="mailto:${personalMobileEmailAddress}">${personalMobileEmailAddress}</a>`;
        vm.otherContact(detailData.contactInformation.otherContactsInfomation);
      }
      vm.detailData(detailData);
    }

    mounted() {
      const contentAreaHeight = $('#contents-area').height();
      const contentHeight = $('#content-cdl010').height();
      if (contentAreaHeight > contentHeight) {
        const subHeight = contentAreaHeight - contentHeight;
        nts.uk.ui.windows.getSelf().setHeight(650 - subHeight);
      }
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

  }

  class ObjectParam {
    employeeId: string;
    referenceDate: any;
  }

  /** 連絡先情報の参照 */
  class ContactInformationRefer {
    /** ビジネスネーム */
    businessName: string = '';
    /** 連絡先情報DTO */
    contactInformation: ContactInformation = new ContactInformation();
    /** 雇用名 */
    employmentName: string = '';
    /** 職場名 */
    workplaceName: string = '';
    /** 分類名 */
    categoryName: string = '';
    /** 職位名 */
    positionName: string = '';

    constructor(init?: Partial<ContactInformationRefer>) {
      $.extend(this, init);
    }
  }

  class ContactInformation {
    /** 会社の携帯電話番号 */
    companyMobilePhoneNumber: string = '';
    /** 個人の携帯電話番号 */
    personalMobilePhoneNumber: string = '';
    /** 緊急連絡先１ */
    emergencyNumber1: string = '';
    /** 緊急連絡先２ */
    emergencyNumber2: string = '';
    /** 座席ダイヤルイン */
    seatDialIn: string = '';
    /** 座席内線番号 */
    seatExtensionNumber: string = '';
    /** 会社のメールアドレス */
    companyEmailAddress: string = '';
    /** 会社の携帯メールアドレス */
    companyMobileEmailAddress: string = '';
    /** 個人のメールアドレス */
    personalEmailAddress: string = '';
    /** 個人の携帯メールアドレス */
    personalMobileEmailAddress: string = '';
    /** 連絡先名 + 連絡先アドレス */
    otherContactsInfomation: OtherContact[] = [];

    constructor(init?: Partial<ContactInformation>) {
      $.extend(this, init);
    }
  }

  class OtherContact {
    /** 連絡先名 */
    contactName: string = '';
    /** 連絡先アドレス */
    contactAddress: string = '';

    constructor(init?: Partial<OtherContact>) {
      $.extend(this, init);
    }
  }

}
