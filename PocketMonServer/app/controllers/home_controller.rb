class HomeController < ApplicationController
  def index
    if current_user
        if current_user.admin == true
            redirect_to rails_admin_path
        else
            render "showerror"
        end     
    end            
  end

  def showerror
  end
end
