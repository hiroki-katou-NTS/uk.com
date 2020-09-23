/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.mgrid.sample {
  const mgrid = nts.uk.ui.mgrid as any;
  const { MGrid, color } = mgrid as Mgrid;

  interface Mgrid {
    color: COLOR;
    MGrid: { new (el: HTMLElement, option: any): { create: () => void } };
  }

  interface COLOR {
    ALL: string[];
    Alarm: "mgrid-alarm";
    Calculation: "mgrid-calc";
    Disable: "mgrid-disable";
    Error: "mgrid-error";
    HOVER: "ui-state-hover";
    Hide: "mgrid-hide";
    Lock: "mgrid-lock";
    ManualEditOther: "mgrid-manual-edit-other";
    ManualEditTarget: "mgrid-manual-edit-target";
    Reflect: "mgrid-reflect";
  }

  /** Khai báo một hằng đối tượng chứa các API cần thiết ở trên đầu như thế này */
  const API = {
    SAVE_DATA: "/abc/xyz",
  };

  // rồi dùng trong view model qua hàm $ajax
  // không cần thiết phải khai báo service.ts riêng ra đâu bởi nó có méo xử lý đặc biệt nào đâu mà phải tách riêng?
  // vm.$ajax(API.SAVE_DATA, { /* body_of_data */ }).then((response) => { /*process after respone*/ })

  @bean()
  export class ViewModel extends ko.ViewModel {

    // Hàm này gọi ngay sau khi viewModel được khởi tạo, nên những
    // xử lý liên quan tới dữ liệu (request lên server, nhận từ màn hình khác)
    // được xử lý ở đây
    created() {
      const vm = this;
      // gọi ajax chỉ cần gọi đơn giản thế này thôi
      vm.$ajax(API.SAVE_DATA, { id: 1, name: "xxxx" }).then((response) => {
        // xử lý các bước logic khác ở đây
      });
    }

    // Hàm này được thực thi khi view đã tồn tại rồI, nên các xử lý liên quan tới view
    // thì đặt vào đây
    mounted() {
      // khai báo biến kiểu hằng (constance) để trỏ vào viewmodel thay cho dùng this
      const vm = this;
      // Nếu một cái gì đó được tái sử dụng lại thì nên gán vào biến để tăng tốc thực thi (chỉ phải khởi tạo có 1 lần thôi)
      const $mgrid = $("#item-list");

      const dataSource: Model[] = [
        {
          // Key của bản ghi, dùng cho các feature
          rowNumber: 1,
          functionName: "Dep trai",
          logLoginDisplay: false,
          logStartDisplay: true,
          logUpdateDisplay: false,
        },
        {
          rowNumber: 2,
          functionName: "Dep trai",
          logLoginDisplay: false,
          logStartDisplay: true,
          logUpdateDisplay: false,
        },
      ];

      // JQuery là một object like array, nên lấy ra giá trị của jQuery container thì có thể lấy bằng index function ($[index])
      // hoặc lấy bằng các hàm hỗ trợ của jQuery như get, nhưng nên lấy bằng các hàm thì hơn bởi jQuery là một object (like array), không phải array.
      new MGrid($mgrid.get(0), {
        subHeight: "450px",
        headerHeight: "60px",
        // Key của mỗi bản ghi (dòng trong grid), sử dụng cho các features
        primaryKey: "rowNumber",
        // Kiểu dữ liệu của key, dùng để so sánh trong các feature.
        primaryKeyDataType: "number",
        rowVirtualization: true,
        virtualization: true,
        virtualizationMode: "continuous",
        enter: "right",
        // dữ liệu này méo cần là observable nếu không phải quan sát cấu trúc dữ liệu của nó
        dataSource,
        columns: [
          {
            headerText: "",
            key: "rowNumber",
            dataType: "number",
            width: "30px",
          },
          {
            headerText: vm.$i18n("CLI002_7"),
            key: "functionName",
            dataType: "string",
            width: "180px",
          },
          {
            headerText: vm.$i18n("CLI002_4"),
            group: [
              {
                headerText: "",
                key: "logLoginDisplay",
                dataType: "boolean",
                width: "180px",
                ntsControl: "Checkbox",
                checkbox: true,
                hidden: false,
              },
            ],
          },
          {
            headerText: vm.$i18n("CLI002_5"),
            group: [
              {
                headerText: "",
                key: "logStartDisplay",
                dataType: "boolean",
                width: "180px",
                ntsControl: "Checkbox",
                checkbox: true,
                hidden: false,
              },
            ],
          },
          {
            headerText: this.$i18n("CLI002_6"),
            group: [
              {
                headerText: "",
                key: "logUpdateDisplay",
                dataType: "boolean",
                width: "180px",
                ntsControl: "Checkbox",
                checkbox: true,
                hidden: false,
              },
            ],
          },
        ],

        features: [
          {
            name: "CellStyles",
            states: [
              {
                // Giá trị key tương ứng với mỗi bản ghi (dòng)
                // Không phải index của bản ghi
                rowId: 1,
                columnKey: "logStartDisplay",
                state: [color.Alarm],
              },
              {
                rowId: 2,
                columnKey: "logStartDisplay",
                state: [color.Calculation],
              },
            ],
          },
          {
            name: "ColumnFixing",
            fixingDirection: "left",
            showFixButtons: false,
            columnSettings: [{ columnKey: "rowNumber", isFixed: true }],
          },
        ],

        ntsFeatures: [
          /*
          {
            name: "CellColor",
            columns: [
              {
                key: "logStartDisplay",
                parse: (value: string) => {
                  debugger;
                  return value;
                },
              },
            ],
          },
       */
        ],

        ntsControls: [
          {
            name: "Checkbox",
            options: { value: 1, text: "" },
            optionsValue: "value",
            optionsText: "text",
            controlType: "CheckBox",
            enable: true,
            onChange: function () {},
          },
        ],
      }).create();
    }
  }

  // Chú ý là dữ liệu có cấu trúc cần và luÔn phải được định kiểu đầy đủ và định kiểu ở dưới này để được IDE sugget đầy đủ
  interface Model {
    
    rowNumber: number;
    functionName: string;
    logLoginDisplay: boolean;
    logStartDisplay: boolean;
    logUpdateDisplay: boolean;
  }
}
