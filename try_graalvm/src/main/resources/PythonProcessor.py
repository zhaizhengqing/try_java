import pprint
import sys
import __graalpython__
pprint.pprint(sys.modules)

def param_decorator(func):
    def convert_foreign_to_buildin(self, *params):
        new_params = list(range(len(params)))
        for i in new_params:
            param = params[i]
            if isinstance(param, __graalpython__.ForeignType):
                if hasattr(param, "indexOf"):
                    new_params[i] = param.toArray()
                elif hasattr(param,"merge"):
                    d = {}
                    for j in param.keySet():
                        d[j] = param.get(j)
                    new_params[i] = d
                elif hasattr(param,"contains"):
                    new_params[i] = set(param.toArray())
                else:
                    raise Exception("unsupported type: {}".format(",".join(dir(param))))
            else:
                new_params[i] = param

        r = func(self, *new_params)
        if isinstance(r, set):
            raise Exception("can not return set")
        return r

    return convert_foreign_to_buildin


class PythonProcessor:
    @param_decorator
    def sign(self, *params):
        print(*params)
        return params[2]


PythonProcessor()