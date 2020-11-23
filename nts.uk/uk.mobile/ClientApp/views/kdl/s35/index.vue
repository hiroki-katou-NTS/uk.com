<template>
  <div class="kdls35">
    <!-- A1_1 -->
    <div class="modal-header rounded-0 d-block p-0">
      <div class="uk-bg-teal p-2">
        <h4 class="modal-title text-white">
          <i class="fas fa-angle-double-left mr-1" v-on:click="back()"></i>
          <span>{{ "KDLS35_1" | i18n }}</span>
        </h4>
      </div>
      <!-- A1_2 -->
      <div class="uk-bg-headline py-3">
        <!-- A1_3 -->
        <span>{{ "KDLS35_2" | i18n }}}</span>
      </div>
    </div>
    <div class="modal-component modal-body m-n3">
      <!-- A2_1 -->
      <div class="row">
        <div class="col-6">
          <nts-label>{{ "KDLS35_3" | i18n }}</nts-label>
        </div>
        <!-- A2_2 -->
        <label class="col-6 pt-2">2020/06/01 ~ 2020/06/03</label>
      </div>
      <!-- A3_1 -->
      <div class="row pt-1">
        <div class="col-6">
          <nts-label>{{ "KDLS35_4" | i18n }}</nts-label>
        </div>
        <!-- A3_2 -->
        <label class="col-6 pt-2">{{ "KDLS35_5" | i18n }}</label>
      </div>
      <div class="row pt-1">
        <div class="col-6">
          <!-- A4_1 -->
          <fa-font icon="fas fa-exclamation-triangle" />
          <!-- A4_2 -->
          <label>{{ "KDLS35_6" | i18n }}</label>
        </div>
        <div class="col-6">
          <!-- A4_3 -->
          <fa-font icon="fas fa-calendar-check" />
          <!-- A4_4 -->
          <label>{{ "KDLS35_7" | i18n }}</label>
        </div>
      </div>
      <!-- A5_1 -->
      <table class="table">
        <thead class="text-center">
          <tr>
            <th scope="col">#</th>
            <!-- A5_2 -->
            <th scope="col">{{ "KDLS35_8" | i18n }}</th>
            <!-- A5_3 -->
            <th scope="col">{{ "KDLS35_9" | i18n }}</th>
            <!-- A5_4 -->
            <th scope="col">{{ "KDLS35_10" | i18n }}</th>
            <!-- A5_5 -->
            <th scope="col">{{ "KDLS35_11" | i18n }}</th>
          </tr>
        </thead>
        <tbody class="text-center">
          <tr>
            <td class="text-justify">
              <!-- A5_6 -->
              <nts-checkbox v-bind:value="1" v-model="checkbox"> </nts-checkbox>
            </td>
            <!-- A5_7 -->
            <td>
              <fa-font icon="fas fa-exclamation-triangle" />
            </td>
            <!-- A5_8 -->
            <td class="text-center">
              {{ date | date("YY/MM/DD(ddd)") }}
            </td>
            <!-- A5_9 -->
            <td>
              <span>0.5$</span>
            </td>
            <!-- A5_10 -->
            <td>
              <span>{{ date | date("YY/MM/DD(ddd)") }}</span>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="modal-footer">
        <!-- A6_1 -->
        <button class="btn btn-link" v-on:click="decide()">
          {{ "KDLS35_12" | i18n }}
        </button>
        <!-- A6_2 -->
        <button class="btn btn-link" v-on:click="back()">
          {{ "KDLS35_13" | i18n }}
        </button>
      </div>
    </div>
  </div>
</template>