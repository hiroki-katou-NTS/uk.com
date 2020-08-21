<template>
  <div class="kafs08a2">
    <div class="step-wizard">
      <step-wizard v-bind:items="['step_1', 'step_2', 'step_3']" v-bind:selected="step" />
    </div>
    <br />
    <!-- A2_1 -->
    <div class="alert error">
      <img
        class="iconWarn"
        src="https://www.iconsdb.com/icons/preview/red/warning-xxl.png"
        width="16"
        height="16"
      />
      <div class="contentError">{{'KAFS08_13'| i18n}}</div>
    </div>
    <!-- A7 -->
    <div class="field-set">
      <!-- A7_1 -->
      <label class="uk-text-dark-gray">{{'KAFS08_20' | i18n}}</label>
      <!-- A7_2 -->
      <div class="a7_2">
        <label>事前事後</label>
      </div>
      <!-- A7_3 -->
      <label class="uk-text-dark-gray">{{'KAFS08_23' | i18n}}</label>
      <!-- A7_4 -->
      <div class="a7_4">
        <label>申請日付</label>
      </div>
      <!-- A7_5 -->
      <div class="uk-text-dark-gray">
        <label class="a7_5">{{'KAFS08_28' | i18n}}</label>
        <!-- A7_6 -->
        <label>出発時刻</label>
      </div>
      <!-- A7_7 -->
      <div class="uk-text-dark-gray">
        <label class="a7_7">{{'KAFS08_29' | i18n}}</label>
        <!-- A7_8 -->
        <label>戻り時刻</label>
      </div>
      <!-- A6 -->
      <div class="field-set">
        <!-- A6_1 -->
        <label>コメント１</label>
      </div>
      <!-- A_10 -->
      <div class="mx-n3">
        <table class="table table-striped">
          <thead>
            <tr>
              <th class="px-1" scope="col">ID</th>
              <th class="px-1" scope="col">First</th>
              <th class="px-1" scope="col">Last</th>
              <th class="px-1" scope="col">Handle</th>
              <th class="px-1 border-right-0" scope="col">Test 5</th>
              <th class="border-left-0"></th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="px-1">03/12/2020</td>
              <td class="px-1">Mark</td>
              <td class="px-1">Otto</td>
              <td class="px-1">@mdo</td>
              <td class="border-right-0 px-1">@kfc</td>
              <td class="text-center px-0 border-left-0">
                <fa-font size="1" icon="angle-right" />
              </td>
            </tr>
            <tr>
              <td class="px-1">03/12/2020</td>
              <td class="px-1">Jacob</td>
              <td class="px-1">Thornton</td>
              <td class="px-1">@fat</td>
              <td class="border-right-0 px-1">@mdo</td>
              <td class="text-center px-0 border-left-0">
                <fa-font v-bind:size="'1'" icon="angle-right" />
              </td>
            </tr>
            <tr>
              <td class="px-1">03/12/2020</td>
              <td class="px-1">Larry</td>
              <td class="px-1">The Bird</td>
              <td class="px-1">@twitter</td>
              <td class="border-right-0 px-1">@mdo</td>
              <td class="text-center px-0 border-left-0">
                <fa-font v-bind:size="'1'" icon="fas fa-angle-right" />
              </td>
            </tr>
            <tr>
              <td class="px-1">03/12/2020</td>
              <td class="px-1">Larry</td>
              <td class="px-1">The Bird</td>
              <td class="px-1">@twitter</td>
              <td class="border-right-0 px-1">@mdo</td>
              <td class="text-center px-0 border-left-0">
                <fa-font v-bind:size="'1'" icon="fas fa-angle-right" />
              </td>
            </tr>
            <tr>
              <td class="px-1">03/12/2020</td>
              <td class="px-1">Larry</td>
              <td class="px-1">The Bird</td>
              <td class="px-1">@twitter</td>
              <td class="border-right-0 px-1">@mdo</td>
              <td class="text-center px-0 border-left-0">
                <fa-font v-bind:size="'1'" icon="fas fa-angle-right" />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- A50_F2 -->
      <div class="process-button">
        <!-- A50_2 -->
        <button class="btn btn-primary btn-block">{{'申請する' | i18n}}</button>
        <!-- A50_3 -->
        <button class="btn btn-secondary btn-block">{{'KAFS08_40' | i18n}}</button>
      </div>

    </div>
  </div>
</template>