/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.cdl010.a.screenModel {

  @bean()
  export class ViewModel extends ko.ViewModel {

    detailData: KnockoutObservable<ContactInformationRefer> = ko.observable(new ContactInformationRefer());
    otherContact: KnockoutObservableArray<OtherContact> = ko.observableArray([]);

    created(param?: ObjectParam) {
      const vm = this;
      // Call API to get ContactInformationRefer
      const mockData: ContactInformationRefer = ({
        businessName: 'ハタケ　カカシ',
        categoryName: '分類１',
        contactInformation: new ContactInformation({
          companyPhone: '080-1234-5678',
          personPhone: '090-1234-xxx8',
          contact1: '080-xxx1-5678',
          contact2: '080-1234-5678',
          seatDial: '123',
          seatNo: '3010',
          cEmail: 'kakashi_hatake@nittsusysutem.co.jp',
          cMobileEmail: 'hatake_kakashi@docomo.ne.jp',
          pEmail: 'hatake_kakashi@gmail.com',
          pMobileEmail: 'hatake_kakashi@docomo.ne.jp',
          otherContact: [
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
      if (mockData.contactInformation) {
        mockData.contactInformation.cEmail = `<a href="mailto:${mockData.contactInformation.cEmail}">${mockData.contactInformation.cEmail}</a>`;
        mockData.contactInformation.cMobileEmail = `<a href="mailto:${mockData.contactInformation.cMobileEmail}">${mockData.contactInformation.cMobileEmail}</a>`;
        mockData.contactInformation.pEmail = `<a href="mailto:${mockData.contactInformation.pEmail}">${mockData.contactInformation.pEmail}</a>`;
        mockData.contactInformation.pMobileEmail = `<a href="mailto:${mockData.contactInformation.pMobileEmail}">${mockData.contactInformation.pMobileEmail}</a>`;
        vm.otherContact(mockData.contactInformation.otherContact);
      }
      vm.detailData(mockData);
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
    companyPhone: string = '';
    /** 個人の携帯電話番号 */
    personPhone: string = '';
    /** 緊急連絡先１ */
    contact1: string = '';
    /** 緊急連絡先２ */
    contact2: string = '';
    /** 座席ダイヤルイン */
    seatDial: string = '';
    /** 座席内線番号 */
    seatNo: string = '';
    /** 会社のメールアドレス */
    cEmail: string = '';
    /** 会社の携帯メールアドレス */
    cMobileEmail: string = '';
    /** 個人のメールアドレス */
    pEmail: string = '';
    /** 個人の携帯メールアドレス */
    pMobileEmail: string = '';
    /** 連絡先名 + 連絡先アドレス */
    otherContact: OtherContact[] = [];

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
