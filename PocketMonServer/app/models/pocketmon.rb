class Pocketmon < ActiveRecord::Base
    def as_json(options = nil)
        options = {:root => true}
            super(options)
    end
end
