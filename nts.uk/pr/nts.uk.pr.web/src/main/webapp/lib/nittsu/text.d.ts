declare module nts.uk.text {
    /**
     * 文字列の半角文字数を数える（Unicode用）
     * @param text 解析対象の文字列
     */
    function countHalf(text: string): number;
    /**
     * 文字列が半角数字のみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    function allHalfNumeric(text: string): boolean;
    /**
     * 文字列が半角英字のみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    function allHalfAlphabet(text: string): boolean;
    /**
     * 文字列が半角英数字のみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    function allHalfAlphanumeric(text: string): boolean;
    /**
     * 文字列が半角カナのみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    function allHalfKatakana(text: string): boolean;
    /**
     * 文字列が全角カナのみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    function allFullKatakana(text: string): boolean;
    /**
     * 文字列が半角文字のみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    function allHalf(text: string): boolean;
    /**
     * 文字列が平仮名のみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    function allHiragana(text: string): boolean;
    /**
     * 文字列がカタカナのみで構成された1文字以上の文字列かどうか判断する
     * @param text 解析対象の文字列
     */
    function allKatakana(text: string): boolean;
    /**
     * 文字列中のHTML記号をサニタイズする
     * @param text 変換対象の文字列
     */
    function htmlEncode(text: string): string;
    /**
     * 1文字目のみ小文字に変換する
     * @param text 変換対象の文字列
     */
    function toLowerCaseFirst(text: string): string;
    /**
     * 1文字目のみ大文字に変換する
     * @param text 変換対象の文字列
     */
    function toUpperCaseFirst(text: string): string;
    /**
    * 指定された文字列が、null、undefined、Emptyか判定する
    * @param text 判定対象の文字列
    */
    function isNullOrEmpty(text: any): boolean;
    /**
    * 指定した文字列の各書式項目を、対応するオブジェクトの値と等価のテキストに置換する
    * @param text 書式文字列
    * @param args 置換の文字列（配列可）
    */
    function format(format: string, ...args: any[]): string;
    /**
    * 日付をフォーマットする
    * @param  {Date}   date     日付
    * @param  {String} [format] フォーマット
    * @return {String}          フォーマット済み日付
    */
    function formatDate(date: any, format: any): any;
    /**
    * 変換文字列の先頭に、文字数分の指定文字列を追加する
    * @param text 変換対象の文字列
    * @param paddingChar 指定文字列
    * @param length 文字数
    */
    function padLeft(text: string, paddingChar: string, length: number): string;
    /**
    * 変換文字列の末尾に、文字数分の指定文字列を追加する
    * @param text 変換対象の文字列
    * @param paddingChar 指定文字列
    * @param length 文字数
    */
    function padRight(text: string, paddingChar: string, length: number): string;
    /**
    * 指定した文字列に、指定した文字列数分、指定文字列を追加する
    * @param text 変換対象の文字列
    * @param paddingChar 埋める文字列
    * @param isPadLeft 左埋めフラグ（false：右埋め）
    * @param length 文字数
    */
    function charPadding(text: string, paddingChar: string, isPadLeft: boolean, length: number): string;
}
