<template>
  <div class="kdls36">
    <!-- A1_1 -->
    <div class="modal-header rounded-0 d-block p-0">
      <div class="uk-bg-teal p-2">
        <h4 class="modal-title text-white">
          <i class="fas fa-angle-double-left mr-1" v-on:click="back()"></i>
          <span>{{ "KDLS36_1" | i18n }}</span>
        </h4>
      </div>
      <!-- A1_2 -->
      <div class="uk-bg-headline py-3">
        <!-- A1_3 -->
        <span>{{ "KDLS36_2" | i18n }}</span>
      </div>
    </div>
    <div class="modal-component modal-body m-n3">
      <!-- A2_1 -->
      <div class="row">
        <div class="col-5">
          <nts-label>{{ "KDLS36_3" | i18n }}</nts-label>
        </div>
        <!-- A2_2 -->
        <label v-if="startDate === endDate" class="col-7 pt-2">{{
          startDate | i18n
        }}</label>
        <label v-else class="col-7 pt-2">{{
          "KDLS36_14" | i18n([startDate, endDate])
        }}</label>
      </div>
      <!-- A3_1 -->
      <div class="row pt-1">
        <div class="col-5">
          <nts-label>{{ "KDLS36_4" | i18n }}</nts-label>
        </div>
        <!-- A3_2 -->
        <label class="col-7 pt-2">{{
          "KDLS36_5" | i18n(requiredNumberOfDays)
        }}</label>
      </div>
      <div class="row pt-1">
        <div class="col-5">
          <!-- A4_1 -->
          <fa-font icon="fas fa-exclamation-triangle" />
          <!-- A4_2 -->
          <label>{{ "KDLS36_6" | i18n }}</label>
        </div>
        <div class="col-7">
          <!-- A4_3 -->
          <fa-font icon="fas fa-calendar-check" />
          <!-- A4_4 -->
          <label>{{ "KDLS36_7" | i18n }}</label>
        </div>
      </div>
      <!-- A5_1 -->
      <table class="table table-responsive">
        <thead class="text-center">
          <tr>
            <th scope="col"></th>
            <!-- A5_2 -->
            <th scope="col">{{ "KDLS36_8" | i18n }}</th>
            <!-- A5_3 -->
            <th scope="col">{{ "KDLS36_9" | i18n }}</th>
            <!-- A5_4 -->
            <th scope="col">{{ "KDLS36_10" | i18n }}</th>
            <!-- A5_5 -->
            <th scope="col">{{ "KDLS36_11" | i18n }}</th>
          </tr>
        </thead>
        <tbody
          v-for="(item, index) in holidayWorkInfoList"
          v-bind:key="index"
        >
          <tr class="text-center">
            <td>
              <!-- A5_6 -->
              <nts-checkbox v-bind:value="true" v-model="item.checked" />
            </td>
            <!-- A5_7 -->
            <td v-on:click="() => item.checked = !item.checked">
              <fa-font 
              v-if="item.dataType == 1" 
              icon="fas fa-calendar-check" 
              />
              <fa-font
                v-else-if="item.expiringThisMonth == true"
                icon="fas fa-exclamation-triangle"
              />
              <fa-font
                v-else icon="fas fa-exclamation-triangle"
              />
            </td>
            <!-- A5_8 -->
            <td class="holidayWorkDate" v-on:click="() => item.checked = !item.checked">
              {{ item.holidayWorkDate | date("YY/MM/DD(ddd)") }}
            </td>
            <!-- A5_9 -->
            <td class="remainingNumber" v-on:click="() => item.checked = !item.checked">
              <span>{{ "KDLS36_5" | i18n(item.remainingNumber) }}</span>
            </td>
            <!-- A5_10 -->
            <td class="expirationDate" v-on:click="() => item.checked = !item.checked">
              <span>{{ item.expirationDate | date("YY/MM/DD(ddd)") }}</span>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="modal-footer">
        <!-- A6_1 -->
        <button class="btn btn-link" v-on:click="decide()">
          {{ "KDLS36_12" | i18n }}
        </button>
        <!-- A6_2 -->
        <button class="btn btn-link" v-on:click="back()">
          {{ "KDLS36_13" | i18n }}
        </button>
      </div>
    </div>
  </div>
</template>