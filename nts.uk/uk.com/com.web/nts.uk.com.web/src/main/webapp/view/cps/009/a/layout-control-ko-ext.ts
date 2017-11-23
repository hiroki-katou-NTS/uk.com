// render primative value to viewContext

module CPS009Constraint {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import random = nts.uk.util.randomId;
    import parseTime = nts.uk.time.parseTime;
    let writeConstraint = window['nts']['uk']['ui']['validation']['writeConstraint'];
    export function primitiveConst(data: any) {
        let dts = data,
            constraint: any = {
                itemName: data.itemName,
                itemCode: data.itemCode,
                required: data.isRequired// !!x.isRequired
            },
            constraints: any;
        if (dts) {
            switch (dts.dataType) {
                default:
                case ITEM_SINGLE_TYPE.STRING:
                    constraint.valueType = "String";
                    constraint.maxLength = dts.stringItemLength || undefined;
                    constraint.stringExpression = '';
                    switch (dts.stringItemType) {
                        default:
                        case ITEM_STRING_TYPE.ANY:
                            constraint.charType = 'Alphabet';
                            break;
                        case ITEM_STRING_TYPE.ANYHALFWIDTH:
                            constraint.charType = 'AnyHalfWidth';
                            break;
                        case ITEM_STRING_TYPE.ALPHANUMERIC:
                            constraint.charType = 'AlphaNumeric';
                            break;
                        case ITEM_STRING_TYPE.NUMERIC:
                            constraint.charType = 'Numeric';
                            if (dts.decimalPart > 0) {
                                constraint.valueType = "Decimal";
                                constraint.mantissaMaxLength = dts.numberDecimalPart;
                            } else {
                                constraint.valueType = "Integer";
                            }
                            constraint.max = dts.numericItemMax || undefined;
                            constraint.min = dts.numericItemMin || 0;
                            break;
                        case ITEM_STRING_TYPE.KANA:
                            constraint.charType = 'Kana';
                            break;
                    }
                    break;
                case ITEM_SINGLE_TYPE.NUMERIC:
                    if (dts.decimalPart == 0) {
                        constraint.valueType = "Integer";
                    } else {
                        constraint.valueType = "Decimal";
                        constraint.mantissaMaxLength = dts.numberDecimalPart;
                    }
                    constraint.charType = 'Numeric';
                    constraint.max = dts.numericItemMax || 99999999;
                    constraint.min = dts.numericItemMin || 0;
                    break;
                case ITEM_SINGLE_TYPE.DATE:
                    constraint.valueType = "Date";
                    constraint.max = parseTime(dts.max, true).format() || undefined;
                    constraint.min = parseTime(dts.min, true).format() || undefined;
                    break;
                case ITEM_SINGLE_TYPE.TIME:
                    constraint.valueType = "Time";
                    constraint.min = parseTime(dts.timeItemMin, true).format();
                    constraint.max = parseTime(dts.timeItemMax, true).format();
                    break;
                case ITEM_SINGLE_TYPE.TIMEPOINT:
                    constraint.valueType = "Clock";
                    constraint.max = parseTime(dts.timepointItemMax, true).format();
                    constraint.min = parseTime(dts.timepointItemMin, true).format();
                    break;
                case ITEM_SINGLE_TYPE.SELECTION:
                    constraint.valueType = "Selection";
                    break;
            }
            writeConstraint(dts.itemCode, constraint);
            return constraint;
        }

    }

    // define ITEM_CLASSIFICATION_TYPE
    enum IT_CLA_TYPE {
        ITEM = 0, // single item
        LIST = 1, // list item
        SPER = 2 // line item
    }

    // define ITEM_CATEGORY_TYPE
    enum IT_CAT_TYPE {
        SINGLE = 1, // Single info
        MULTI = 2, // Multi info
        CONTINU = 3, // Continuos history
        NODUPLICATE = 4, //No duplicate history
        DUPLICATE = 5 // Duplicate history
    }

    // defined CATEGORY or GROUP mode
    enum CAT_OR_GROUP {
        CATEGORY = 0, // category mode
        GROUP = 1 // group mode
    }

    // define ITEM_TYPE is set or single item
    enum ITEM_TYPE {
        SET = 1, // List item info
        SINGLE = 2 // Single item info
    }

    // define ITEM_SINGLE_TYPE
    // type of item if it's single item
    enum ITEM_SINGLE_TYPE {
        STRING = 1,
        NUMERIC = 2,
        DATE = 3,
        TIME = 4,
        TIMEPOINT = 5,
        SELECTION = 6
    }

    // define ITEM_STRING_DATA_TYPE
    enum ITEM_STRING_DTYPE {
        FIXED_LENGTH = 1, // fixed length
        VARIABLE_LENGTH = 2 // variable length
    }

    enum ITEM_STRING_TYPE {
        ANY = 1,
        // 2:全ての半角文字(AnyHalfWidth)
        ANYHALFWIDTH = 2,
        // 3:半角英数字(AlphaNumeric)
        ALPHANUMERIC = 3,
        // 4:半角数字(Numeric)
        NUMERIC = 4,
        // 5:全角カタカナ(Kana)
        KANA = 5
    }

    // define ITEM_SELECT_TYPE
    // type of item if it's selection item
    enum ITEM_SELECT_TYPE {
        // 1:専用マスタ(DesignatedMaster)
        DESIGNATED_MASTER = 1,
        // 2:コード名称(CodeName)
        CODE_NAME = 2,
        // 3:列挙型(Enum)
        ENUM = 3
    }
}