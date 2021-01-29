/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg005.a.screenModel {
  import object = nts.uk.at.view.ccg005.a.object;

  const API = {
    getDisplayAttendanceData: 'screen/com/ccg005/get-display-attendance-data',
  };
  const ID_AVATAR_CHANGE = 'ccg005-avatar-change';

  @component({
    name: 'ccg005-component',
    template: `<div style="display: flex; position: relative; overflow-x: hidden; overflow-y: auto; height: 450px" id="ccg005-watching">
    <div id="ccg005-content">
      <div style="margin: 10px;">
        <div class="grade-header-top">
          <!-- A0 -->
          <span data-bind="i18n: 'CCG005_1'" class="ccg005-bold"></span>
          <!-- A1_5 -->
          <i data-bind="ntsIcon: {no: 5, width: 25, height: 25}, click: $component.openScreenCCG005B"></i>
          &#160;
          <!-- A1_6 -->
          <i data-bind="ntsIcon: {no: 194, width: 25, height: 25}"></i>
        </div>
        <div class="grade-header-center" style="padding-bottom: 5px;">
          <table>
            <tr>
              <td>
                <!-- A1_1 -->
                <div id="ccg005-avatar-change"></div>
              </td>
              <td style="padding-left: 5px; width: 370px;">
                <!-- A1_2 -->
                <span class="ccg005-bold" data-bind="text: $component.businessName()"></span>
                <div class="ccg005-flex">
                  <!-- A1_3 -->
                  <i data-bind="ntsIcon: {no: $component.emoji(), width: 20, height: 20}"></i>
                  <!-- A1_4 -->
                  <span data-bind="text: $component.goOutReason()"></span>
                </div>
              </td>
              <td>
                <!-- A1_7 -->
                <i class="ccg005-status-img" data-bind="ntsIcon: {no: 196, width: 20, height: 20}"></i>
              </td>
            </tr>
          </table>
        </div>
        <div class="grade-header-bottom ccg005-flex" style="position: relative;">
          <!-- A2_3 -->
          <div data-bind="ntsDatePicker: {
            name: '#[CCG005_36]',
            value: date,
            dateFormat: 'YYYY/MM/DD',
            fiscalMonthsMode: true,
            showJumpButtons: true
          }"></div>
          <!-- A2_1 -->
          <button id="ccg005-legends" style="margin-left: 5px;" data-bind="ntsLegendButton: legendOptions"></button>
          <div style="right: 0; position: absolute; display: flex; align-items: center;">
            <!-- A3_2 -->
            <i id="ccg005-star-img" style="margin-right: 5px;" data-bind="ntsIcon: {no: 184, width: 20, height: 20}"></i>
            <!-- A3_1 -->
            <div data-bind="ntsComboBox: {
              width: '120px',
              options: itemList,
              editable: true,
              visibleItemsCount: 5,
              value: selectedCode,
              optionsValue: 'code',
              optionsText: 'name',
              required: true,
              columns: [
                { prop: 'name' }
              ]}"></div>
          </div>
        </div>
        <div class="grade-body-top" style="padding-top: 5px;">
          <table style="width: 100%;">
            <tr>
              <td style="width: 35px;"></td>
              <!-- A2_5 -->
              <td class="ccg005-w100 ccg005-pl-5">
                <span class="ccg005-bold" data-bind="i18n: 'CCG005_41'"></span>
              </td>
              <!-- A2_6 -->
              <td class="ccg005-w100 ccg005-pl-5">
                <span class="ccg005-bold" data-bind="i18n: 'CCG005_42'"></span>
              </td>
              <td></td>
              <td></td>
            </tr>
          </table>
        </div>
        <!-- A5 -->
        <div class="grade-body-bottom" style="min-height: 55px; height: 275px;">
          <table style="width: 100%; border-collapse: separate; border-spacing: 0 5px">
            

            <tr style="background-color: yellow; height: 50px;">
              <td style="padding-right: 5px; width: 30px; background-color: white;">
                <!-- A4_1 -->
                <img style="border-radius: 50%; border: 1px groove;" width="30px" height="30px" src="${__viewContext.rootPath}/view/ccg/005/resource/AVERAGE.png"/>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset">
                <!-- A4_8 -->
                <div>text 1</div>
                <!-- A4_5 -->
                <div>
                  <i data-bind="ntsIcon: {no: $component.emoji(), width: 20, height: 20}"></i>
                </div>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <div>
                  <!-- A4_2 -->
                  <span>text 2</span>
                  <!-- A4_4 -->
                  <i data-bind="ntsIcon: {no: 190, width: 13, height: 13}"></i>
                </div>
                <!-- A4_3 -->
                <div>texxt 1</div>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <!-- A4_7 -->
                <span class="ccg005-flex">
                  <i class="ccg005-status-img" data-bind="ntsIcon: {no: 191, width: 20, height: 20}"></i>
                </span>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-left-unset">
                <!-- A4_6 time -->
                <div>10:25 - 15:45</div>
                <!-- A4_6 text -->
                <div>急に用事があるの</div>
              </td>
            </tr>

            <tr style="background-color: yellow; height: 50px;">
              <td style="padding-right: 5px; width: 30px; background-color: white;">
                <!-- A4_1 -->
                <img style="border-radius: 50%; border: 1px groove;" width="30px" height="30px" src="${__viewContext.rootPath}/view/ccg/005/resource/AVERAGE.png"/>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset">
                <!-- A4_8 -->
                <div>text 1</div>
                <!-- A4_5 -->
                <div>
                  <i data-bind="ntsIcon: {no: $component.emoji(), width: 20, height: 20}"></i>
                </div>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <div>
                  <!-- A4_2 -->
                  <span>text 2</span>
                  <!-- A4_4 -->
                  <i data-bind="ntsIcon: {no: 190, width: 13, height: 13}"></i>
                </div>
                <!-- A4_3 -->
                <div>texxt 1</div>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <!-- A4_7 -->
                <span class="ccg005-flex">
                  <i class="ccg005-status-img" data-bind="ntsIcon: {no: 191, width: 20, height: 20}"></i>
                </span>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-left-unset">
                <!-- A4_6 time -->
                <div>10:25 - 15:45</div>
                <!-- A4_6 text -->
                <div>急に用事があるの</div>
              </td>
            </tr>

            <tr style="background-color: yellow; height: 50px;">
              <td style="padding-right: 5px; width: 30px; background-color: white;">
                <!-- A4_1 -->
                <img style="border-radius: 50%; border: 1px groove;" width="30px" height="30px" src="${__viewContext.rootPath}/view/ccg/005/resource/AVERAGE.png"/>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset">
                <!-- A4_8 -->
                <div>text 1</div>
                <!-- A4_5 -->
                <div>
                  <i data-bind="ntsIcon: {no: $component.emoji(), width: 20, height: 20}"></i>
                </div>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <div>
                  <!-- A4_2 -->
                  <span>text 2</span>
                  <!-- A4_4 -->
                  <i data-bind="ntsIcon: {no: 190, width: 13, height: 13}"></i>
                </div>
                <!-- A4_3 -->
                <div>texxt 1</div>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <!-- A4_7 -->
                <span class="ccg005-flex">
                  <i class="ccg005-status-img" data-bind="ntsIcon: {no: 191, width: 20, height: 20}"></i>
                </span>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-left-unset">
                <!-- A4_6 time -->
                <div>10:25 - 15:45</div>
                <!-- A4_6 text -->
                <div>急に用事があるの</div>
              </td>
            </tr>

            <tr style="background-color: yellow; height: 50px;">
              <td style="padding-right: 5px; width: 30px; background-color: white;">
                <!-- A4_1 -->
                <img style="border-radius: 50%; border: 1px groove;" width="30px" height="30px" src="${__viewContext.rootPath}/view/ccg/005/resource/AVERAGE.png"/>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset">
                <!-- A4_8 -->
                <div>text 1</div>
                <!-- A4_5 -->
                <div>
                  <i data-bind="ntsIcon: {no: $component.emoji(), width: 20, height: 20}"></i>
                </div>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <div>
                  <!-- A4_2 -->
                  <span>text 2</span>
                  <!-- A4_4 -->
                  <i data-bind="ntsIcon: {no: 190, width: 13, height: 13}"></i>
                </div>
                <!-- A4_3 -->
                <div>texxt 1</div>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <!-- A4_7 -->
                <span class="ccg005-flex">
                  <i class="ccg005-status-img" data-bind="ntsIcon: {no: 191, width: 20, height: 20}"></i>
                </span>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-left-unset">
                <!-- A4_6 time -->
                <div>10:25 - 15:45</div>
                <!-- A4_6 text -->
                <div>急に用事があるの</div>
              </td>
            </tr>

            <tr style="background-color: yellow; height: 50px;">
              <td style="padding-right: 5px; width: 30px; background-color: white;">
                <!-- A4_1 -->
                <img style="border-radius: 50%; border: 1px groove;" width="30px" height="30px" src="${__viewContext.rootPath}/view/ccg/005/resource/AVERAGE.png"/>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset">
                <!-- A4_8 -->
                <div>text 1</div>
                <!-- A4_5 -->
                <div>
                  <i data-bind="ntsIcon: {no: $component.emoji(), width: 20, height: 20}"></i>
                </div>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <div>
                  <!-- A4_2 -->
                  <span>text 2</span>
                  <!-- A4_4 -->
                  <i data-bind="ntsIcon: {no: 190, width: 13, height: 13}"></i>
                </div>
                <!-- A4_3 -->
                <div>texxt 1</div>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <!-- A4_7 -->
                <span class="ccg005-flex">
                  <i class="ccg005-status-img" data-bind="ntsIcon: {no: 191, width: 20, height: 20}"></i>
                </span>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-left-unset">
                <!-- A4_6 time -->
                <div>10:25 - 15:45</div>
                <!-- A4_6 text -->
                <div>急に用事があるの</div>
              </td>
            </tr>



          </table>
        </div>
        <div class="grade-bottom ccg005-flex" style="width: 100%; align-items: center; position: relative; margin-top: 10px; margin-bottom: 10px;">
          <table style="width: 100%;">
            <tr>
              <td>
                <div class="ccg005-pagination ccg005-flex" style="align-items: center;">
                  <!-- A5_1 -->
                  <i class="ccg005-pagination-btn" data-bind="ntsIcon: {no: 193, width: 15, height: 20}, click: $component.previousPage"></i>
                  <!-- A5_2 -->
                  <span style="white-space: nowrap;" data-bind="text: $component.paginationText()"></span>
                  <!-- A5_3 -->
                  <i class="ccg005-pagination-btn" data-bind="ntsIcon: {no: 192, width: 15, height: 20}, click: $component.nextPage"></i>
                </div>
              </td>
              <td style="width: 100%">
                <div class="ccg005-switch-btn" style="float: right;">
                  <!-- A5_4 -->
                  <div class="cf" data-bind="ntsSwitchButton: {
                    options: contentSelections,
                    optionsValue: 'code',
                    optionsText: 'name',
                    value: contentSelected }"></div>
                </div>
              </td>
            </tr>
          </table>
        </div>
      </div>

      <!-- A3_2 Popup -->
      <div id="ccg005-star-popup" style="width: 120px;">
        <!-- A3_2.1 -->
        <table>
          <tr>
            <td style="text-align: right;">
              <a style="color: blue; text-decoration: underline;" data-bind="i18n: 'CCG005_34', click: $component.openScreenCCG005D"></a>
            </td>
          </tr>
          <tr>
            <td style="padding-top: 10px;">
              <!-- A3_2.2 -->
              <input data-bind="ntsTextEditor: {
                value: searchValue,
                enterkey: submit,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                  textmode: 'text',
                  width: '100px',
                  placeholder: $component.placeholder()
                }))
              }"></input>
            </td>
          </tr>
          <tr>
            <td style="padding-top: 10px;">
              <!-- A3_2.3 -->
              <button data-bind="i18n: 'CCG005_32'"></button>
              <!-- A3_2.4 -->
              <span data-bind="text: label3_2"></span>
            </td>
          </tr>
        </table>
      </div>
      <!-- A1_7 & A4_7 Popup -->
      <div id="ccg005-status-popup">
        <table>
          <tr data-bind="click: $component.openScreenCCG005E">
            <td>
              <i data-bind="ntsIcon: {no: 78, width: 15, height: 25}"></i>
            </td>
            <!-- A1_7.1 -->
            <td>
              <i data-bind="ntsIcon: {no: 196, width: 20, height: 20}"></i>
            </td>
            <td data-bind="i18n: 'CCG005_43'"></td>
          </tr>
          <tr data-bind="click: $component.openScreenCCG005E">
            <td>
              <i data-bind="ntsIcon: {no: 78, width: 15, height: 25}"></i>
            </td>
            <!-- A1_7.2 -->
            <td>
            <i data-bind="ntsIcon: {no: 195, width: 20, height: 20}"></i>
            </td>
            <td data-bind="i18n: 'CCG005_44'"></td>
          </tr>
          <tr data-bind="click: $component.openScreenCCG005E">
            <td>
              <i data-bind="ntsIcon: {no: 78, width: 15, height: 25}"></i>
            </td>
            <!-- A1_7.3 -->
            <td>
            <i data-bind="ntsIcon: {no: 191, width: 20, height: 20}"></i>
            </td>
            <td data-bind="i18n: 'CCG005_39'"></td>
          </tr>
          <tr data-bind="click: $component.openScreenCCG005E">
            <td>
              <i data-bind="ntsIcon: {no: 78, width: 15, height: 25}"></i>
            </td>
            <!-- A1_7.4 -->
            <td>
            <i data-bind="ntsIcon: {no: 196, width: 20, height: 20}"></i>
            </td>
            <td data-bind="i18n: 'CCG005_44'"></td>
          </tr>
          <tr data-bind="click: $component.openScreenCCG005E">
            <td>
              <i data-bind="ntsIcon: {no: 78, width: 15, height: 25}"></i>
            </td>
            <!-- A1_7.5 -->
            <td>
            <i data-bind="ntsIcon: {no: 197, width: 20, height: 20}"></i>
            </td>
            <td data-bind="i18n: 'CCG005_40'"></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <style>
    .ccg005-w100 {
      width: 100px;
    }
    .ccg005-bold {
      font-weight: bold;
    }
    .ccg005-pl-5 {
      padding-left: 5px;
    }
    .ccg005-border-groove {
      border: 1px groove;
    }
    .ccg005-right-unset {
      border-right: none;
    }
    .ccg005-left-unset {
      border-left: none;
    }
    .ccg005-flex {
      display: flex;
    }
    .grade-header-top {
      display: flex;
      margin-top: 5px;
    }
    .grade-header-top span {
      width: 390px;
    }
    .bg-white-ccg005 {
      background-color: #FFFFFF;
    }
    .bg-green-ccg005 {
      background-color: #99FF99
    }
    .bg-yellow-ccg005 {
      background-color: #FFFF00
    }
    .bg-gray-ccg005 {
      background-color: #D9D9D9
    }
    #ccg005-content {
      width: calc(100% - 5px);
      border: 1px groove black;
    }
    #ccg005-status-popup table tr,
    #ccg005-content i, #ccg005-content img {
      cursor: pointer;
    }
    #ccg005-status-popup table tr td {
      padding-right: 5px;
    }
    #CCG005_A1_1 {
      max-height: 40px;
      max-width: 40px;
      min-height: 40px;
      min-width: 40px;
      border-radius: 50%;
    }
    #CCG005_no_avatar {
      display: flex;
      align-items: center;
      background-color: #eeeeee;
      color: blue;
      border: 1px solid #333688;
      border-radius: 50%;
      width: 30px;
      height: 30px;
    }
  </style>`
  })
  export class ViewModel extends ko.ViewModel {
    date: KnockoutObservable<string> = ko.observable('20000101');
    legendOptions: any = {
      items: [
        { cssClass: { className: 'bg-white-ccg005', colorPropertyName: 'background-color' }, labelText: this.$i18n('CCG005_26') },  // A2_1.1
        { cssClass: { className: 'bg-green-ccg005', colorPropertyName: 'background-color' }, labelText: this.$i18n('CCG005_22') },  // A2_1.2
        { cssClass: { className: 'bg-yellow-ccg005', colorPropertyName: 'background-color' }, labelText: this.$i18n('CCG005_39') }, // A2_1.3
        { cssClass: { className: 'bg-gray-ccg005', colorPropertyName: 'background-color' }, labelText: this.$i18n('CCG005_40') }    // A2_1.4
      ]
    };
    contentSelections: KnockoutObservableArray<any> = ko.observableArray([
      { code: '1', name: this.$i18n('CCG005_37') },
      { code: '2', name: this.$i18n('CCG005_38') }
    ]);
    contentSelected: KnockoutObservable<any> = ko.observable(1);
    selectedCode: KnockoutObservable<string> = ko.observable('1');
    searchValue: KnockoutObservable<string> = ko.observable('');
    label3_2: KnockoutObservable<string> = ko.observable('label3_2');
    itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([
      new ItemModel('1', '基本給'),
      new ItemModel('2', '役職手当'),
      new ItemModel('3', '基本給な')
    ]);

    placeholder: KnockoutObservable<string> = ko.observable(this.$i18n('CCG005_45'));
    height: KnockoutObservable<number> = ko.observable(465);
    // Pagination
    currentPage: KnockoutObservable<number> = ko.observable(1);
    perPage: KnockoutObservable<number> = ko.observable(5);
    totalElement: KnockoutObservable<number> = ko.observable(7);
    totalRow: KnockoutComputed<number> = ko.computed(() =>
      this.perPage() * this.currentPage() > this.totalElement()
        ? this.totalElement() - this.perPage() * (this.currentPage() - 1)
        : this.perPage());
    startPage: KnockoutComputed<number> = ko.computed(() => this.perPage() * (this.currentPage() - 1) + 1);
    endPage: KnockoutComputed<number> = ko.computed(() => this.startPage() + this.totalRow() - 1);
    paginationText: KnockoutComputed<string> = ko.computed(() => `${this.startPage()}-${this.endPage()}/${this.totalElement()}`);
    // End pagination

    created() {
      const vm = this;
      vm.toStartScreen();
    }

    mounted() {
      const vm = this;
      $('#ccg005-legends').click(() => {
        $('.legend-item-symbol').first().css('border', '1px groove');
      });
      vm.initResizeable(vm);
      vm.initPopupArea();
      vm.initPopupStatus();
      vm.perPage.subscribe(() => vm.currentPage(1));
    }

    initResizeable(vm: any) {
      $(window).on('ccg005.resize', () => {
        const subHeight = $('#ccg005-content').height()
          - $('.grade-header-top').height()
          - $('.grade-header-center').height()
          - $('.grade-header-bottom').height()
          - $('.grade-body-top').height()
          - $('.grade-bottom').height()
          - 40;
        if (subHeight >= 53) {
          vm.perPage(_.floor(subHeight/53));
        }
        $('.grade-body-bottom').height(subHeight);
      });
    }

    /**
     * Popup A3_2
     */
    private initPopupArea() {
      $('#ccg005-star-popup').ntsPopup({
        position: {
            my: 'left top',
            at: 'right top',
            of: $('#ccg005-star-img')
        },
        showOnStart: false,
        dismissible: true
      });
      $('#ccg005-star-img').click(() => $('#ccg005-star-popup').ntsPopup('toggle'));
    }

    /**
     * Popup A1_7 & A4_7
     */
    private initPopupStatus() {
      _.forEach($('.ccg005-status-img'), element => {
        $('#ccg005-status-popup').ntsPopup({
          position: {
              my: 'left top',
              at: 'right top',
              of: element
          },
          showOnStart: false,
          dismissible: true
        });
        $(element).click(() => {
          $('#ccg005-status-popup').ntsPopup({
            position: {
                my: 'left top',
                at: 'right top',
                of: $(element)
            },
            showOnStart: false,
            dismissible: true
          });
          $('#ccg005-status-popup').ntsPopup('toggle');
        });
      });
    }

    nextPage() {
      const vm = this;
      if (vm.currentPage() * vm.perPage() < vm.totalElement()) {
        vm.currentPage(vm.currentPage() + 1);
      }
    }

    previousPage() {
      const vm = this;
      if (vm.currentPage() > 1) {
        vm.currentPage(vm.currentPage() - 1);
      }
    }

    submit() {}

    openScreenCCG005B() {
      const vm = this;
      vm.$window.modal('/view/ccg/005/b/index.xhtml', {});
    }

    openScreenCCG005D() {
      const vm = this;
      vm.$window.modal('/view/ccg/005/d/index.xhtml', {});
    }

    openScreenCCG005E() {
      const vm = this;
      vm.$window.modal('/view/ccg/005/e/index.xhtml', {
      //社員ID				
      sid: "213",				
      //ビジネスネーム		
      businessName: "haha",			
      //年月日		
      goOutDate: moment.utc()						
      });
    }

    activityStatus: KnockoutObservable<ActivityStatus> = ko.observable(new ActivityStatus());
    businessName: KnockoutObservable<string> = ko.observable('');
    emoji: KnockoutObservable<number> = ko.observable(187);
    goOutReason: KnockoutObservable<string> = ko.observable('');
    avatarPath: KnockoutObservable<string> = ko.observable('');

    private toStartScreen() {
      const vm = this;
      const loginSid = __viewContext.user.employeeId;
      vm.$blockui('grayout');
      vm.$ajax('com', API.getDisplayAttendanceData).then((response: object.DisplayAttendanceDataDto) => {
        // A1_2 表示初期の在席データDTO.自分のビジネスネーム
        vm.businessName(response.bussinessName);
        if (response && response.attendanceInformationDtos) {
          // 条件：在席情報DTO.社員ID＝ログイン社員ID
          const atdInfo = _.find(response.attendanceInformationDtos, item => item.sid === loginSid);
          if (!atdInfo) {
            return;
          }
          // A1_1 表示初期の在席データDTO.在席情報DTO.個人の顔写真.顔写真ファイルID
          if (atdInfo.avatarDto && atdInfo.avatarDto.fileId) {
            $(`#${ID_AVATAR_CHANGE}`)
              .append($("<img/>")
                .attr("id", 'CCG005_A1_1')
                .attr("src", (nts.uk.request as any).liveView(atdInfo.avatarDto.fileId))
              );
          } else {
            $(`#${ID_AVATAR_CHANGE}`).ready(() => {
              $(`#${ID_AVATAR_CHANGE}`).append(
                `<div id='CCG005_no_avatar'>
                  <p style="text-align: center; margin: 0 auto; font-size: 12px">
                    ${vm.businessName().replace(/\s/g, '').substring(0, 2)}
                  </p>
                </div>`
              );
            });
          }
          if (atdInfo.activityStatusDto) {
            // 表示初期の在席データDTO.在席情報DTO.在席のステータス
            vm.activityStatus(new ActivityStatus({
              activity: atdInfo.activityStatusDto.activity,
              date: atdInfo.activityStatusDto.date,
              sid: atdInfo.activityStatusDto.sid
            }));
          }
          // A1_3
          if (atdInfo.emojiDto) {
            // 表示初期の在席データDTO.在席情報DTO.社員の外出情報.感情種類
            vm.initEmojiType(atdInfo.emojiDto.emojiType);
          }
          // A1_4
          if (atdInfo.goOutDto) {
            vm.goOutReason(atdInfo.goOutDto.goOutReason);
          }
        }
      }).always(() => vm.$blockui('clear'));
    }

    initEmojiType(emojiType: number) {
      const vm = this;
      switch(emojiType) {
        case EmojiType.WEARY:
          vm.emoji(189);
          break;
        case EmojiType.SAD:
          vm.emoji(188);
          break;
        case EmojiType.AVERAGE:
          vm.emoji(187);
          break;
        case EmojiType.GOOD:
          vm.emoji(186);
          break;
        case EmojiType.HAPPY:
          vm.emoji(185);
          break;
      }
    }

    initActivityStatus(activity: number) {

    }
  }

  class ItemModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
        this.code = code;
        this.name = name;
    }
  }

  class ActivityStatus {
    activity: number;
    date: any;
    sid: string;

    constructor(init?: ActivityStatus) {
      $.extend(this, init);
    }
  }
  
  enum EmojiType {
    WEARY = 0, // どんより
    SAD = 1, // ゆううつ
    AVERAGE = 2, // 普通
    GOOD = 3, // ぼちぼち
    HAPPY = 4 // いい感じ
  }
}
