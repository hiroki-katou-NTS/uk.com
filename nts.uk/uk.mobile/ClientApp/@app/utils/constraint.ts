import { obj } from '@app/utils';
import { IRule } from 'declarations';

const charTypes: {
    [key: string]: {
        width: number;
        viewName: string;
    }
} = {
    AnyHalfWidth: {
        width: 0.5,
        viewName: '半角'
    },
    AlphaNumeric: {
        width: 0.5,
        viewName: '半角英数字'
    },
    Alphabet: {
        width: 0.5,
        viewName: '半角英字'
    },
    Numeric: {
        width: 0.5,
        viewName: '半角数字'
    },
    Any: {
        width: 1,
        viewName: '全角'
    },
    Kana: {
        width: 1,
        viewName: 'カナ'
    },
    HalfInt: {
        width: 0.5,
        viewName: '半整数'
    },
    WorkplaceCode: {
        width: 0.5,
        viewName: '半角英数字'
    },
    EmployeeCode: {
        width: 0.5,
        viewName: '半角英数字'
    }
};

export const constraint = {
    html(prmitive: IRule) {
        let $content = '';

        if (obj.isEmpty(prmitive)) {
            return $content;
        }

        switch (prmitive.valueType) {
            case 'String':
                let char = charTypes[prmitive.charType] || charTypes.Any;

                $content += `${char.viewName}${constraint.getCharLength(prmitive)}文字`;
                break;
            case 'Date':
                break;
            case 'Time':
                $content += constraint.getTimeMinMax(prmitive);
                break;
            case 'Clock':
                $content += constraint.getTimeMinMax(prmitive);
                break;
            case 'Duration':
                $content += constraint.getTimeMinMax(prmitive);
                break;
            case 'TimePoint':
                $content += constraint.getTimeMinMax(prmitive);
                break;
            case 'Decimal':
            case 'Integer':
                // $content += $content.length > 0 ? "/" : "";
                $content += constraint.getMinMax(prmitive);
                break;
            default:
                break;
        }

        return $content;
    },
    getMinMax(prmitive: IRule): string {
        return `${prmitive.min}～${prmitive.max}`;
    },
    getTimeMinMax(prmitive: IRule): string {
        // Thêm đoạn convert từ number sang time
        return '';
    },
    getCharLength(prmitive: IRule): number {
        let char = charTypes[prmitive.charType] || charTypes.Any;

        return Math.floor(prmitive.maxLength / (char.width * 2));
    },
    charTypes
};