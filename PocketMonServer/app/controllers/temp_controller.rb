class TempController < ApplicationController
  def check
    @temp = Temp.new
    @temp.tempint = [params:tempint]
    @temp.tempfloat = [params:tempfloat]
    @temp.save
  end
end
